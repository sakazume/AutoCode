package entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Persistence;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import lombok.Data;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;
import common.AbstractClass;


/**
 * Entity共通クラス
 * @author t-sakazume
 *
 */
@MappedSuperclass
@Data
public abstract class AbstraactEntity<T extends EntityPathBase<?>> extends AbstractClass {
	/** EntityManager基本的に使わないと思う */
	static final EntityManager em;
	static final JPAQuery query;
	static {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("example");
	      em = factory.createEntityManager();
	      query = new JPAQuery(em);
	}

	@JsonIgnore
	@Transient
	private T t ;

	@Id
	@GeneratedValue
	Long id;

	@Version
	Timestamp version;
	
	
	public AbstraactEntity(){
		super();
	}

	protected AbstraactEntity(T ent) {
		this();
		t = ent;
	}
	@Transient
	/**
	 * IDでの検索
	 * @param id
	 * @param clz ジェネリクス
	 * @return
	 */
	public <X extends AbstraactEntity<?>> X findById(Long id) {

		@SuppressWarnings("unchecked")
		Class<X> type = (Class<X>) this.getClass();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<X> cq = cb.createQuery(type);
		Root<X> r = cq.from(type);
		cq.select(r).where(cb.equal(r.get("id"), id));
		List<X> userList = em.createQuery(cq).getResultList();

		if(userList.size()==0) {
			return null;
		} else {
			return userList.get(0);
		}
		
	}


	@Transient
	/**
	 * 保存の実行
	 */
	public void  save() {
		// insert(登録)
		em.getTransaction().begin();
		em.persist(this);
		em.getTransaction().commit();
	}

	protected JPAQuery from() {
		return query.from(t);
	}

}
