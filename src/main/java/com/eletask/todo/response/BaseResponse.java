package com.eletask.todo.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseResponse<T> {

   private boolean isSuccess = true;
   private String message = "";

   private T payload;


}
