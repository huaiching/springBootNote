-- 流程定義表
DROP TABLE IF EXISTS flow_definition;

CREATE TABLE IF NOT EXISTS flow_definition (
    id                     SERIAL,
    module_type            VARCHAR(50),        -- 模組類型，例如 claim, inquiry
    flow_type              CHAR(1),            -- 流程類型，例如 M=主流程 / S=子流程
    current_status         VARCHAR(10),        -- 目前狀態代碼
    current_status_desc    VARCHAR(50),        -- 目前狀態中文
    next_status            VARCHAR(10),        -- 下一狀態
    prew_status            VARCHAR(10),        -- 上一狀態
    expression             VARCHAR(250)        -- 檢核規則
);

CREATE UNIQUE INDEX index_1 ON flow_definition(id);
CREATE        INDEX index_2 ON flow_definition(module_type);
CREATE        INDEX index_3 ON flow_definition(flow_type);
CREATE        INDEX index_4 ON flow_definition(current_status);

-- 預設流程定義
INSERT INTO flow_definition VALUES (DEFAULT, 'claim', 'M', NULL, '新流程', 'M01', NULL, '#status.isEmpty()');
INSERT INTO flow_definition VALUES (DEFAULT, 'claim', 'M', 'M01', '建檔', 'M02', NULL, '#status == ''M01''');
INSERT INTO flow_definition VALUES (DEFAULT, 'claim', 'M', 'M02', '審核', 'M03', 'M01', '#status == ''M02''');
INSERT INTO flow_definition VALUES (DEFAULT, 'claim', 'M', 'M03', '送核', 'M04', 'M02', '#status == ''M03''');
INSERT INTO flow_definition VALUES (DEFAULT, 'claim', 'M', 'M04', '結案', NULL, 'M03', '#status == ''M04''');

INSERT INTO flow_definition VALUES (DEFAULT, 'inquiry', 'S', NULL, '照會-新流程', 'S01', NULL, '#status.isEmpty()');
INSERT INTO flow_definition VALUES (DEFAULT, 'inquiry', 'S', 'S01', '照會-建檔', 'S02', NULL, '#status == ''S01''');
INSERT INTO flow_definition VALUES (DEFAULT, 'inquiry', 'S', 'S02', '照會-回覆', 'S03', 'S01', '#status == ''S02''');
INSERT INTO flow_definition VALUES (DEFAULT, 'inquiry', 'S', 'S03', '照會-結案', NULL, 'S02', '#status == ''S03''');

-- 案件主流程狀態表
CREATE TABLE IF NOT EXISTS claim_main_status (
    case_uuid     VARCHAR(36)  NOT NULL,        -- 當前流程 UUID
    main_uuid     VARCHAR(36)  NOT NULL,        -- 主流程 UUID
    client_id     VARCHAR(10)  NOT NULL,        -- 客戶證號
    clfp_seq      INTEGER      NOT NULL,        -- 建檔編號
    module_type   VARCHAR(50)  NOT NULL,        -- 模組類型，例如 claim, inquiry
    status        VARCHAR(10)  NOT NULL,        -- 目前節點
    owner_user    VARCHAR(50),                  -- 負責人
    note          VARCHAR(255),                 -- 簽核意見
    process_user  CHAR(8),                      -- 處理者
    process_date  CHAR(9),                      -- 處理日期
    process_time  CHAR(8)                       -- 處理時間
);

CREATE UNIQUE INDEX index_1 ON claim_main_status(case_uuid);
CREATE        INDEX index_2 ON claim_main_status(main_uuid);
CREATE        INDEX index_3 ON claim_main_status(client_id);
CREATE        INDEX index_4 ON claim_main_status(client_id, clfp_seq);
CREATE        INDEX index_5 ON claim_main_status(owner_user);

-- 案件子流程狀態表
CREATE TABLE IF NOT EXISTS claim_sub_status (
    case_uuid     VARCHAR(36)  NOT NULL,        -- 當前流程 UUID
    main_uuid     VARCHAR(36)  NOT NULL,        -- 主流程 UUID
    sub_uuid      VARCHAR(36)  NOT NULL,        -- 子流程 UUID
    client_id     VARCHAR(10)  NOT NULL,        -- 客戶證號
    clfp_seq      INTEGER      NOT NULL,        -- 建檔編號
    module_type   VARCHAR(50)  NOT NULL,        -- 模組類型，例如 claim, inquiry
    status        VARCHAR(10)  NOT NULL,        -- 目前節點
    owner_user    VARCHAR(50),                  -- 負責人
    note          VARCHAR(255),                 -- 簽核意見
    process_user  CHAR(8),                      -- 處理者
    process_date  CHAR(9),                      -- 處理日期
    process_time  CHAR(8)                       -- 處理時間
);

CREATE UNIQUE INDEX index_1 ON claim_sub_status(case_uuid);
CREATE        INDEX index_2 ON claim_sub_status(client_id);
CREATE        INDEX index_3 ON claim_sub_status(client_id, clfp_seq);
CREATE        INDEX index_4 ON claim_sub_status(owner_user);

-- 案件歷程表
CREATE TABLE IF NOT EXISTS claim_history (
    case_uuid     VARCHAR(36)  NOT NULL,    -- 當前流程 UUID
    main_uuid     VARCHAR(36)  NOT NULL,    -- 主流程 UUID
    sub_uuid      VARCHAR(36) ,             -- 子流程 UUID
    client_id     VARCHAR(50)  NOT NULL,    -- 客戶證號
    clfp_seq      INTEGER      NOT NULL,    -- 建檔編號
    flow_type     CHAR(1)      NOT NULL,    -- 流程類型，例如 M=主流程 / S=子流程
    module_type   VARCHAR(50)  NOT NULL,    -- 模組類型，例如 claim, inquiry
    main_status   VARCHAR(10)  NOT NULL,    -- 主流程 目前節點
    sub_status    VARCHAR(10),              -- 子流程 目前節點
    owner_user    VARCHAR(50),              -- 負責人
    note          VARCHAR(255),             -- 簽核意見
    process_user  CHAR(8),                  -- 處理者
    process_date  CHAR(9),                  -- 處理日期
    process_time  CHAR(8)                   -- 處理時間
);

CREATE UNIQUE INDEX index_1 ON claim_history(case_uuid, main_uuid, sub_uuid);
CREATE        INDEX index_2 ON claim_history(main_uuid);
CREATE        INDEX index_3 ON claim_history(sub_uuid);
CREATE        INDEX index_4 ON claim_history(client_id);
CREATE        INDEX index_5 ON claim_history(client_id, clfp_seq);

-- clnm 流程表
DROP TABLE IF EXISTS clnm_flow;
CREATE TABLE IF NOT EXISTS clnm_flow (
    sub_uuid        VARCHAR(36)  NOT NULL    -- 子流程 UUID
    -- 剩餘欄位待議
);

-- cldh 流程表
DROP TABLE IF EXISTS cldh_flow;
CREATE TABLE IF NOT EXISTS cldh_flow (
    sub_uuid        VARCHAR(36)  NOT NULL    -- 子流程 UUID
    -- 剩餘欄位待議
);

-- clpn 流程表
DROP TABLE IF EXISTS clpn_flow;
CREATE TABLE IF NOT EXISTS clpn_flow (
    sub_uuid        VARCHAR(36)  NOT NULL    -- 子流程 UUID
    -- 剩餘欄位待議
);