public class QueryConstant {
   public static String TABLE_REQUIRED="SELECT table_name\n" + "FROM information_schema.tables\n" + "WHERE table_type='BASE TABLE'\n" + "AND table_schema='public'";
   public static String EXPLAIN="explain";
}

