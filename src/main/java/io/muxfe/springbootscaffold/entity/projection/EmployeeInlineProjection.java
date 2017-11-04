package io.muxfe.springbootscaffold.entity.projection;

import io.muxfe.springbootscaffold.entity.Department;
import io.muxfe.springbootscaffold.entity.Employee;
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
