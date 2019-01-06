package ${rootPackage}.service;

import ${rootPackage}.dao.${modelName}Mapper;
import ${rootPackage}.model.${modelName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by CodeX4J.
 */
@Service
public class ${modelName}Service {
    @Autowired
    private ${modelName}Mapper ${modelName?uncap_first}Mapper;

    public int add(${modelName} ${modelName?uncap_first}) {
        return ${modelName?uncap_first}Mapper.insert(${modelName?uncap_first});
    }

    public ${modelName} find(int id) {
        return ${modelName?uncap_first}Mapper.selectByPrimaryKey(id);
    }

    public int update(${modelName} ${modelName?uncap_first}) {
        return ${modelName?uncap_first}Mapper.updateByPrimaryKeySelective(${modelName?uncap_first});
    }

    public int delete(int id) {
        return ${modelName?uncap_first}Mapper.deleteByPrimaryKey(id);
    }
}