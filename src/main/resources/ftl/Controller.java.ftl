package ${rootPackage}.controller;

import ${rootPackage}.model.${modelName};
import ${rootPackage}.service.${modelName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by CodeX4J.
 */
@Controller
@RequestMapping("${modelName?uncap_first}")
@ResponseBody
public class ${modelName}Controller {
    @Autowired
    private ${modelName}Service ${modelName?uncap_first}Service;
    
    @RequestMapping("add")
    public int add(${modelName} ${modelName?uncap_first}) {
        return ${modelName?uncap_first}Service.add(${modelName?uncap_first});
    }
    
    @RequestMapping("find")
    public ${modelName} find(int id) {
        return ${modelName?uncap_first}Service.find(id);
    }
    
    @RequestMapping("update")
    public int update(${modelName} ${modelName?uncap_first}) {
        return ${modelName?uncap_first}Service.update(${modelName?uncap_first});
    }
    
    @RequestMapping("delete")
    public int delete(int id) {
        return ${modelName?uncap_first}Service.delete(id);
    }
}