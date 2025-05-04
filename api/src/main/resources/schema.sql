-- 客戶資料檔
CREATE TABLE IF NOT EXISTS clnt (
    client_id   CHAR(10),   -- 客戶證號
    names       CHAR(40),   -- 客戶姓名
    sex         CHAR(1),    -- 客戶性別
    age         INTEGER     -- 客戶年齡
);

-- 客戶地址檔
CREATE TABLE IF NOT EXISTS addr (
    client_id   CHAR(10),   -- 客戶姓名
    addr_ind    CHAR(1),    -- 地址指示
    address     CHAR(72),   -- 地址
    tel         CHAR(11)    -- 電話
);