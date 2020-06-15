public class Configuration {
    public final static int DATABASE_PORT = 6001;
    public final static String DATABASE_ADDRESS = "localhost";
    public final static String DATABASE_PROVIDER = "postgresql";
    public final static String DATABASE_NAME = "TPC-H";
    public final static String USERNAME = "postgres";
    public final static String PASSWORD = "root";
    public static final String CREATE_TABLE_QUERY_TXT_FILE ="create_table_query.txt" ;
    public static final String SELECT_QUERY_TXT_FILE ="select_query.txt" ;
    public static final String DATABASE_INFO = "database_info.txt";
    public static final String TRANSACTION_QUERY_FILE ="update_query.txt";
    public static final  String CREATE_MODE="createMode";
    public static final  String TRANSACTIONAL_MODE="transactionMode";
    public static final String ANALYTICAL_MODE="analyticalMode";
    public static final String COMPARE_DATABASE_INFO = "compare_database_info.txt";
}
