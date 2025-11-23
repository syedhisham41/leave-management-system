ALTER TABLE leave_request ADD COLUMN approver_comments TEXT DEFAULT NULL;
ALTER TABLE leave_audit_log ADD COLUMN approver_comments TEXT DEFAULT NULL;
