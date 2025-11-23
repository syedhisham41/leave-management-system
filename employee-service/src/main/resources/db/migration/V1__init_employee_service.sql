-- Employees Table
CREATE TABLE IF NOT EXISTS employees (
    emp_id INTEGER PRIMARY KEY AUTOINCREMENT,
    emp_name TEXT NOT NULL,
    emp_joining_date TEXT NOT NULL,
    emp_department_id TEXT NOT NULL,
    emp_email TEXT UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (emp_department_id) REFERENCES department(id)
);

-- Department Table
CREATE TABLE IF NOT EXISTS department (
    id TEXT PRIMARY KEY,                         -- e.g., 'ENG', 'HR', 'GEN'
    name TEXT NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Department Manager Table
CREATE TABLE IF NOT EXISTS department_manager (
    department_id TEXT NOT NULL,
    manager_id INTEGER NOT NULL,
    role TEXT NOT NULL CHECK(role IN ('SUPERVISOR', 'MANAGER')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY (department_id, role),
    FOREIGN KEY (department_id) REFERENCES department(id) ON DELETE CASCADE,
    FOREIGN KEY (manager_id) REFERENCES employees(emp_id) ON DELETE CASCADE
);
