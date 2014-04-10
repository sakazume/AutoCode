package entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.Data;


@Data
@Entity
public class Config extends AbstraactEntity<QConfig> {

//	@Transient
//	QConfig config;

	/** 設定ユーザ */
    @OneToOne
    @JoinColumn(name="user_id")
 	User user;

	/** 設定値タイプ*/
	String configType;

	/** 設定値JSON*/
	@Lob
	String valueJson;

	public Config() {
		super(QConfig.config);
	}

	public Config findByUser(User user) {
		QConfig config = getT();

		return this.from()
				.leftJoin(this.getT().user, QUser.user)
				.where(
						user.getT().id.eq(user.getId()))
						.uniqueResult(config);

	}

}
