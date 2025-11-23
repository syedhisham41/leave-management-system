package utils.dto;

import java.util.ArrayList;
import java.util.List;

import dto.DTO_department_manager;
import dto.DTO_employee;
import model.DepartmentManager;
import model.Employee;

public class Utils_dto {

	public static DTO_employee mapEmployeeToDTO(Employee emp) {

		DTO_employee emp_DTO = new DTO_employee();
		emp_DTO.setName(emp.getName());
		emp_DTO.setDepartment(emp.getEmp_department_id());
		emp_DTO.setEmail_id(emp.getEmail());
		emp_DTO.setId(emp.getId());

		return emp_DTO;
	}

	public static List<DTO_department_manager> mapManagerToDTO(List<DepartmentManager> manager_list) {

		List<DTO_department_manager> manager_list_DTO = new ArrayList<>();

		for (DepartmentManager each : manager_list) {
			DTO_department_manager manager_DTO = new DTO_department_manager();
			manager_DTO.setDepartment_id(each.getDepartment_id());
			manager_DTO.setManager_id(each.getManager_id());
			manager_DTO.setRole(each.getRole());

			manager_list_DTO.add(manager_DTO);
		}

		return manager_list_DTO;
	}

}
