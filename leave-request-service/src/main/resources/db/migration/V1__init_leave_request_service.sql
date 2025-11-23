CREATE TABLE IF NOT EXISTS leave_request (
    leave_id INTEGER PRIMARY KEY AUTOINCREMENT,
    employee_id INTEGER NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    half_day_type TEXT CHECK(half_day_type IN ('NONE', 'FIRST_DAY_HALF', 'LAST_DAY_HALF', 'BOTH')) DEFAULT 'NONE',
    leave_type TEXT CHECK(leave_type IN (
        'SICK', 'CASUAL', 'EARNED', 'WFH', 'BEREAVEMENT', 'MATERNITY', 'PATERNITY'
    )) NOT NULL,
    status TEXT CHECK(status IN ('PENDING', 'APPROVED', 'REJECTED', 'CANCELLED')) DEFAULT 'PENDING',
    approver_id INTEGER, -- optional at time of application, validated by service logic
    applied_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    decision_on TIMESTAMP,
    reason TEXT
);

CREATE TABLE IF NOT EXISTS leave_balance (
    employee_id INTEGER NOT NULL,
    leave_type TEXT CHECK(leave_type IN (
        'SICK', 'CASUAL', 'EARNED', 'WFH', 'BEREAVEMENT', 'MATERNITY', 'PATERNITY'
    )) NOT NULL,
    balance REAL DEFAULT 0,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (employee_id, leave_type)
);

CREATE TABLE IF NOT EXISTS leave_audit_log (
    audit_id INTEGER PRIMARY KEY AUTOINCREMENT,
    leave_id INTEGER NOT NULL,
    action TEXT CHECK(action IN ('APPLIED', 'APPROVED', 'REJECTED', 'CANCELLED', 'AUTO_REJECTED')) NOT NULL,
    performed_by INTEGER,
    performed_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    comments TEXT,
	leave_days REAL DEFAULT NULL,
    FOREIGN KEY (leave_id) REFERENCES leave_request(leave_id)
);
