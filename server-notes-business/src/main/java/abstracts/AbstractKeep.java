package abstracts;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import utils.MessageUtil;

public abstract class AbstractKeep<model, to> extends AbstractSession {	
	protected static String BREAK_LINE = "\n";
	
	@PersistenceContext(unitName = "notes_datasource")
	private EntityManager entityManager;
	private ModelMapper converter = new ModelMapper();
	
	private Class<?> modelClass;
	private Class<?> toClass;
	
	public AbstractKeep(Class<model> modelClass, Class<to> toClass) {
		this.setModelClass(modelClass);
		this.setToClass(toClass);
	}
	
	@SuppressWarnings("unchecked")
	public List<to> convertModelResults(List<model> results) {		
		return (List<to>) results.stream().map(model -> this.getConverter().map(model, this.getToClass()))
				.collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	public to convertToDTO(model obj) {
		if (obj == null) {
			return null;
		} 
		
		return (to) this.getConverter().map(obj, this.getToClass());
	}

	@SuppressWarnings("unchecked")
	public model convertToModel(to obj) {
		return (model) this.getConverter().map(obj, this.getModelClass());
	}
	
	// Getters and Setters
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public String getLabel(String key) {
		return MessageUtil.getMessageFromProperties(key);
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ModelMapper getConverter() {
		return converter;
	}

	public void setConverter(ModelMapper converter) {
		this.converter = converter;
	}

	public Class<?> getModelClass() {
		return modelClass;
	}

	public void setModelClass(Class<?> modelClass) {
		this.modelClass = modelClass;
	}

	public Class<?> getToClass() {
		return toClass;
	}

	public void setToClass(Class<?> toClass) {
		this.toClass = toClass;
	}

}