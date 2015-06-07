package models.service.factory;

import models.service.ModelService;
import play.db.ebean.Model;

/**
 * ModelServiceのファクトリクラス
 *
 * @author リックス
 *
 */
public class ModelServiceFactory {

    private static final ModelServiceFactory instance = new ModelServiceFactory();

    private ModelServiceFactory() { }

    public static ModelServiceFactory getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T extends Model> ModelService<T> create(Class<T> modelClass) throws ReflectiveOperationException {
        String serviceName = modelClass.getSimpleName() + "Service";

        Class<ModelService<T>> modelServiceClass = (Class<ModelService<T>>) Class.forName("models.entity.service.impl." + serviceName);

        return modelServiceClass.newInstance();
    }
}
