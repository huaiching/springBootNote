-- 流程定義表
DROP TABLE IF EXISTS flow_definition;

CREATE TABLE IF NOT EXISTS flow_definition (
    id              BIGINT AUTO_INCREMENT,
    flow_type       VARCHAR(50),        -- 流程類型，例如 "CLAIM"
    current_status  VARCHAR(10),        -- 目前節點代碼，如 "1","2","a"
    next_status     VARCHAR(10),        -- 下一節點代碼，可為 NULL
    prew_status     VARCHAR(10),        -- 上一節點代碼，可為 NULL
    spel_expression VARCHAR(250)        -- SpEL 檢核規則，可為 NULL
);

CREATE UNIQUE INDEX index_1 ON flow_definition(id);
CREATE        INDEX index_2 ON flow_definition(flow_type);
CREATE        INDEX index_3 ON flow_definition(current_status);

-- 插入預設流程定義 (CLAIM 流程)
INSERT INTO flow_definition (flow_type, current_status, next_status, prew_status, spel_expression) VALUES
('CLAIM', NULL, '1', NULL, '#status.isEmpty()'),                     -- 新案件 → 1
('CLAIM', '1',  '2', NULL, '#status == ''1'''),                       -- 1:建檔 → 2
('CLAIM', '2',  '3', '1',  '#status == ''2'' and #subFlow == false'),  -- 2:審核 → 3 (可退回1)
('CLAIM', '2',  'A', '2',  '#status == ''2'' and #subFlow == true'),   -- 2:審核 → A (照會)
('CLAIM', 'A',  '2', '2',  '#status == ''A'''),                       -- a:照會 → 2
('CLAIM', '3',  '4', '2',  '#status == ''3'''),                       -- 3:送核 → 4
('CLAIM', '4',  NULL, '3',  '#status == ''4''');                      -- 4:結案 → 無


-- 案件狀態表
CREATE TABLE IF NOT EXISTS claim_status (
    uuid          VARCHAR(36)  NOT NULL,        -- UUID
    client_id     VARCHAR(10)  NOT NULL,        -- 客戶證號
    claim_seq     INTEGER  NOT NULL,         -- 建檔編號
    status        VARCHAR(10)  NOT NULL,        -- 目前節點
    process_user  VARCHAR(50),                  -- 處理者
    process_date  CHAR(9),                      -- 處理日期
    process_time  CHAR(8)                       -- 處理時間
);

CREATE UNIQUE INDEX index_1 ON claim_status(uuid);
CREATE        INDEX index_2 ON claim_status(client_id);
CREATE        INDEX index_3 ON claim_status(client_id, claim_seq);

-- 案件歷程表
CREATE TABLE IF NOT EXISTS claim_history (
    uuid          VARCHAR(36)  NOT NULL,    -- UUID
    client_id     VARCHAR(50)  NOT NULL,    -- 客戶證號
    claim_seq     INTEGER  NOT NULL,    -- 建檔編號
    status        VARCHAR(10)  NOT NULL,    -- 目前節點
    process_user  VARCHAR(50),              -- 處理者
    process_date  CHAR(9),                  -- 處理日期
    process_time  CHAR(8)                   -- 處理時間
);

CREATE UNIQUE INDEX index_1 ON claim_history(uuid);
CREATE        INDEX index_2 ON claim_history(client_id);
CREATE        INDEX index_3 ON claim_history(client_id, claim_seq);