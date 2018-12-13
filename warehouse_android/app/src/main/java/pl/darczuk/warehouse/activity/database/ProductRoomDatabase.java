package pl.darczuk.warehouse.activity.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import pl.darczuk.warehouse.activity.dao.ProductDao;
import pl.darczuk.warehouse.activity.model.Product;

@Database(entities = {Product.class}, version = 1 )
public abstract class ProductRoomDatabase extends RoomDatabase {

    public abstract ProductDao productDao();

    private static volatile ProductRoomDatabase INSTANCE;

    public static ProductRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProductRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room
                                    .databaseBuilder(context.getApplicationContext(),
                                        ProductRoomDatabase.class, "product_database")
                                    //.addMigrations(MIGRATION_1_2)
                                    .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ProductDao mDao;

        public PopulateDbAsync(ProductRoomDatabase instance) {
            mDao = instance.productDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.nuke();
            return null;
        }
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // database.execSQL("ALTER TABLE \"product\" DROP COLUMN \"quantity\"; "); // impossible drop column
            database.execSQL("ALTER TABLE \"product\" ADD COLUMN \"serverQuantity\" int; ");
            database.execSQL("ALTER TABLE \"product\" ADD COLUMN \"localDeltaChangeQuantity\" int; ");
        }
    };
}
