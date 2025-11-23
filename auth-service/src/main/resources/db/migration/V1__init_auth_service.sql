CREATE TABLE IF NOT EXISTS users (
  emp_id INTEGER PRIMARY KEY,                     -- same as employee-service
  email TEXT UNIQUE NOT NULL,                     -- cannot be changed
  password_hash TEXT NOT NULL,                    -- secure hash
  role TEXT NOT NULL CHECK(role IN ('EMPLOYEE', 'MANAGER', 'ADMIN')) DEFAULT 'EMPLOYEE',
  is_active BOOLEAN DEFAULT TRUE,                 -- for future deactivation
  last_login_at TIMESTAMP,                        -- updated on each login
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  salt TEXT NOT NULL
);