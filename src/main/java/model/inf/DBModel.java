package model.inf;

import java.io.IOException;

import model.ConfigParamModel.DBConfigParam;
import entity.User;

public interface DBModel {
	/**
	 * DB接続ファイルを作成
	 * @param dbConfig
	 */
	public void updatePersistenceFile(DBConfigParam dbConfig, User user)throws IOException;

	/** DB接続ファイル保存 */
	public void dbConfigPut(DBConfigParam dbConfig);
	public void dbConfigDelete(DBConfigParam dbConfig);

}
