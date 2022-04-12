package com.marsamaroc.gestionengins.mapper;

import org.springframework.data.domain.Page;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class EntityToDTO<Entity,EntityDTO>{
    private Class<Entity> classEntity;
    private Class<EntityDTO> classEntiteDTO;
    public List<EntityDTO> mappingToDTO(List<Entity> entities){
        List<EntityDTO> entityDTOS = new ArrayList<>();
        Class<EntityDTO> EntityDTO = null;
        for(Entity entity : entities)
            entityDTOS.add(createContents(entity));
        return entityDTOS;
    }
    public Page<EntityDTO> mappingToDTO(Page<Entity> entities){
        Page<EntityDTO> entityDTOS = entities.map(new Function<Entity, EntityDTO>() {
            @Override
            public EntityDTO apply(Entity entity) {
                return createContents(entity);
            }
        }) ;
        return entityDTOS;
    }
    public EntityToDTO(Class<Entity> entityClass, Class<EntityDTO> entityDTOClass){
        this.classEntiteDTO = entityDTOClass;
        this.classEntity = entityClass;
    }
    EntityDTO createContents( Entity entity) {
        try {
                return classEntiteDTO.getConstructor(classEntity).newInstance(entity);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

}
