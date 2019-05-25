package com.iwhalecloud.retail.workflow.mapper;

import com.iwhalecloud.retail.workflow.WorkFlowServiceApplication;
import com.iwhalecloud.retail.workflow.entity.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = WorkFlowServiceApplication.class)
@RunWith(SpringRunner.class)
public class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public  void insertINTO(){
        Task task=new Task();
        task.setFormId(String.valueOf(System.currentTimeMillis()));
        taskMapper.insert(task);
        System.out.println(task.getTaskId());
    }

}