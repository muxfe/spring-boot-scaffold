package io.muxfe.springbootstarter.entity.projection;

import io.muxfe.springbootstarter.entity.Department;
import io.muxfe.springbootstarter.entity.Employee;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "inline", types = Employee.class)
interface EmployeeInlineProjection {

  Long getId();
  String getEname();
  String getJob();
  Employee getMgr();
  Long getHiredate();
  Double getSal();
  Double getComm();
  Department getDepartment();
}
