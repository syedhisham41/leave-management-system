package utils.service;

import java.time.temporal.ChronoUnit;

import constants.enums.Half_day_types;
import dto.DTO_leave_request;
import dto.DTO_leave_request_get;

public class Utils_service {

	public static double getNumberofLeaveDays(DTO_leave_request leave_request) {

		double daysOfLeaveRequested = ChronoUnit.DAYS.between(leave_request.getStart_date(),
				leave_request.getEnd_date()) + 1;
		if (leave_request.getHalf_day_type().equals(Half_day_types.FIRST_DAY_HALF)
				|| leave_request.getHalf_day_type().equals(Half_day_types.LAST_DAY_HALF))
			daysOfLeaveRequested = daysOfLeaveRequested - 0.5;
		if (leave_request.getHalf_day_type().equals(Half_day_types.BOTH))
			daysOfLeaveRequested = daysOfLeaveRequested - 1;

		return daysOfLeaveRequested;
	}
	
	public static double getNumberofLeaveDays(DTO_leave_request_get leave_request) {

		double daysOfLeaveRequested = ChronoUnit.DAYS.between(leave_request.getStart_date(),
				leave_request.getEnd_date()) + 1;
		if (leave_request.getHalf_day_type().equals(Half_day_types.FIRST_DAY_HALF)
				|| leave_request.getHalf_day_type().equals(Half_day_types.LAST_DAY_HALF))
			daysOfLeaveRequested = daysOfLeaveRequested - 0.5;
		if (leave_request.getHalf_day_type().equals(Half_day_types.BOTH))
			daysOfLeaveRequested = daysOfLeaveRequested - 1;

		return daysOfLeaveRequested;
	}
}
