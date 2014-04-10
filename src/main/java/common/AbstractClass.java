package common;



import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 全クラス共通クラス
 * ログの設定くらいしかないと思う
 * @author styu03
 *
 */
public abstract class AbstractClass {
	
	protected static final String BASE_DIR = "F:/JAVA/IDE/eclipse4.3/AutoCode/";
	
	
	/**
	 * ロガー
	 * @return
	 */
	public Logger log(){
		return LoggerFactory.getLogger(this.getClass());
	}

	
	/**
	 * デバッグログ
	 * @param str
	 */
	public void debug(String str) {
		log().debug(str);
	}
	
	
	/**
	 * オブジェクトをJSON文字列へパースする
	 * @param t 変換オブジェクト
	 * @return JSON文字列
	 */
	protected <T> String toJSON(T t) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(t);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	public String toJSON() {
		return toJSON(this);
	}

	/**
	 * セッターリフレクション
	 * JSみたいにチェーンメソッドで呼びたいときに使用
	 * @param parm
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractClass> T set(String parm , String value) {
		try {
			PropertyDescriptor pd = new PropertyDescriptor(parm, this.getClass());
			//セッターメソッドを取得・実行
			Method w = pd.getWriteMethod();
			w.invoke(this, new Object[] { value });
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return (T) this;
	}
}
