package com.example.cs110.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WineDAO 
{
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TYPE = "type";
    public static final String KEY_ORIGIN = "origin";  
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_FAVORITES = "favorites";
    public static final String KEY_WISH = "wish";
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "wine";
    private static final String DATABASE_TABLE = "wines";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
        "create table wines (_id integer primary key autoincrement, "
        + "name text not null, type text not null, " 
        + "origin text not null, description text not null, "
        + "favorites integer not null, wish integer not null);";
        
    private final Context context; 
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public WineDAO(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
        int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion 
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS wines");
            onCreate(db);
        }
    }    
    
    //opens the database
    public WineDAO open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //closes the database
    public void close() 
    {
        DBHelper.close();
    }
    
    //insert a wine into the database
    public long insertWine(String name, String type, String origin, String description, int favorites, int wish) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_TYPE, type);
        initialValues.put(KEY_ORIGIN, origin);
        initialValues.put(KEY_DESCRIPTION, description);
        initialValues.put(KEY_FAVORITES, favorites);
        initialValues.put(KEY_WISH, wish);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //deletes a particular wine
    public boolean deleteWine(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + 
        		"=" + rowId, null) > 0;
    }
    
    //deletes entire database
    public int deleteAllWines()
    {
    	return db.delete(DATABASE_TABLE, null, null);
    }

    //retrieves all the wines
    public Cursor getAllWines() 
    {
        return db.query(DATABASE_TABLE, new String[] {
        		KEY_ROWID, 
        		KEY_NAME,
        		KEY_TYPE,
                KEY_ORIGIN,
                KEY_DESCRIPTION,
                KEY_FAVORITES,
                KEY_WISH}, 
                null, 
                null, 
                null, 
                null, 
                KEY_NAME + " ASC");
    }

    //retrieves a particular wine
    public Cursor getWine(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_NAME, 
                		KEY_TYPE,
                		KEY_ORIGIN,
                		KEY_DESCRIPTION,
                		KEY_FAVORITES,
                		KEY_WISH
                		}, 
                		KEY_ROWID + "=" + rowId, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //updates a wine
    public boolean updateWine(long rowId, String name, 
    String type, String origin, String desc, int favorites, int wish) 
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_TYPE, type);
        args.put(KEY_ORIGIN, origin);
        args.put(KEY_DESCRIPTION, desc);
        args.put(KEY_FAVORITES, favorites);
        args.put(KEY_WISH, wish);
        return db.update(DATABASE_TABLE, args, 
                         KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    //Add current wine to favorites or wish list
    public long addToList(Cursor f, int favorites, int myWish){
    	ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, f.getString(1));
        initialValues.put(KEY_TYPE, f.getString(2));
        initialValues.put(KEY_ORIGIN, f.getString(3));
        initialValues.put(KEY_DESCRIPTION, f.getString(4));
        initialValues.put(KEY_FAVORITES, favorites);
        initialValues.put(KEY_WISH, myWish);
        long rowId = f.getLong(0);
        return db.update(DATABASE_TABLE, initialValues, 
                KEY_ROWID + "=" + rowId, null);
    }
    
    //populate database
    public void populateDatabase(){
    	ContentValues initialValues = new ContentValues();
    	
    	// RED
    	// =====================================
    	
        initialValues.put(KEY_NAME, "Armailhac");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "Lovely expression of spicy, slightly exotic black fruits, beautiful lift of freshness and briary charm - a lovely wine for the medium term.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Batailley");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "Robust, rather earthy fruit, then a touch of violets to lift it and a hint of leather, good wine, good future.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Bon Pasteur");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "The ripe tannins in this wine give a beautiful impression and the ripe yet reserved fruit match nicely. Full and very integrated. Elegant and sexy.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Branaire Ducru");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "A very pretty red with flowers, blackberries, and blueberries. Full and silky with racy tannins and a long finish. Some hints of cocoa and pleasant bitterness. Well structured.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Canon");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "A wine with balance and freshness with an orange peel and blueberry character. Medium to full body, with firm tannins and clean, bright finish. Merlot 70% and 30% Cabernet Franc.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Canon la Gaffeliere");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "There is a nice mix of flavours on the nose with the sweetness of the black fruits balanced by fresher red. The mid palate seems quite light with raspberries and wild strawberries but the black fruits are there behind their richness filling out the finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Cantemerle");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "Bilberry and bramble give freshness on the nose and palate. There are richer flavours in the middle quite sweet cassis backed by firmer black cherry. The underlying tannins are firm tending to hold back the fruit and shorten the finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Capbern Gasqueton");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "Some pleasant fruit with a earthy, spicy finish. A little light. 74% Cabernet Sauvignon and 26% Merlot.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Carruades de Lafite");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "A wine with sweet tobacco and coffee character. Full body, with velvety tannins and a medium finish. The Petit Verdot comes out here with spices and hints of fresh herbs. Blend of 55% Cabernet Sauvignon, 39% Merlot 3.5% Cabernet Franc, and 2.5% Petit Verdot.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Chapelle d'Ausone");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "The nose is black fruited quite light but with attractive fragrances. The fruit on the mid palate feels ripe the rounded tannins giving suppleness and towards the back the layers of flavour give some complexity.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "La Chapelle de la Mission");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "Floral summer fruits, clarity and purity. Quite vigourous, it needs time to soften.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Leoville Poyferre");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "The nose is rich deep black fruited profound. It feels fresher on the start of the palate with hints of red fruits that lighten and bring out a touch of spice. The back palate is sweet fruited well supported by the tannic structure.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Clos Fourtet");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "A beautiful wine, with everything in the bottle. Blackberries, minerals and blueberries. Full and silky. Long, long finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Charmes Chambertin");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Burgundy");
        initialValues.put(KEY_DESCRIPTION, "Dark red berries, flowers, mint and spices are woven together beautifully in this lush, generous Charmes.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Gevrey Chambertin");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Burgundy");
        initialValues.put(KEY_DESCRIPTION, "The detailed, delicious and vibrant middle weight flavors possess good depth and solid persistence.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Gevrey Lavaux St Jacques");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Burgundy");
        initialValues.put(KEY_DESCRIPTION, "there is excellent vibrancy and punch to the intensely mineral-inflected medium weight flavors that possess fine delineation and a lovely sense of underlying tension on the complex, persistent and palate staining finish. This is really quite impressive and worth considering.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Vosne Petit Monts");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Burgundy");
        initialValues.put(KEY_DESCRIPTION, "A more restrained and slightly less lavishly spiced nose features ripe dark berry fruit and earth aromas that merge gracefully into the focused, concentrated and firm middle weight flavors that are supported by tannins that are less fine but there is notably more complexity and slightly better length as well. ");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Cornas");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Rhene");
        initialValues.put(KEY_DESCRIPTION, "Cornas exhibits classic notes of blackberries, cassis, camphor, incense, violets and hints of forest floor as well as steak tartare, a sensational texture, a full-bodied opulence, and terrific richness. This remarkable Cornas can be drunk early or cellared for 15-20 years.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Mon Coeur");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Rhene");
        initialValues.put(KEY_DESCRIPTION, "This dense ruby/purple-colored, rich wine reveals kirsch and black currant fruit intermixed with notions of spice box, pepper and meat. Luscious and round.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Cuvee Quet");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Rhene");
        initialValues.put(KEY_DESCRIPTION, " Its dense purple color is accompanied by an exquisite bouquet of boysenberries, mulberries, blueberries and other wild mountain blue and black fruits. Possessing great intensity, a full-bodied mouthfeel and superb purity, it is a singular expression of old vine Grenache blended with Mourvedre.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Cete Retie");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Rhene");
        initialValues.put(KEY_DESCRIPTION, "Opaque purple. Explosively perfumed scents of red and dark berries, potpourri and Asian spices, with black pepper and mineral nuances gaining strength with air. Juicy and expansive on the palate, showing intense black raspberry and blueberry flavors and an exotic touch of candied violet.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Hermitage Rouge");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Rhene");
        initialValues.put(KEY_DESCRIPTION, "Hermitage exhibits a black/purple color along with a sumptuous nose of roasted meats, ground pepper, black currants, blackberry jam, and subtle smoke and licorice. The extraordinary bouquet is followed by a wine of extravagant intensity as well as tremendous focus and precision.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Cheteauneuf-du-Pape");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Rhene");
        initialValues.put(KEY_DESCRIPTION, "A blend of 85% Grenache and the rest equal parts Mourvedre, Vaccarese, and Syrah, all aged in old tanks, it exhibits a deep ruby/purple color as well as a sumptuous bouquet of black raspberries, kirsch liqueur, and subtle notions of underbrush and nori seaweed wrapper. Rayas-like in its ethereal richness, length, and texture with a sense of lightness despite its weight, this beauty possesses superb purity, equilibrium, texture, and elegance.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Les Origines");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Rhene");
        initialValues.put(KEY_DESCRIPTION, "Grenache Noir 50%, Syrah 20%, Mourvedre 30%. Aged in barrique and cuve. Pretty, floral elements on the nose. Rather restrained and feminine. A little low key with tannins just very slightly austere.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Cote Rotie Landonne");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Rhene");
        initialValues.put(KEY_DESCRIPTION, "It exhibits notes of asphalt, blackberries, charcoal, truffles, roasted meats and creme de cassis. Full-bodied as well as extraordinarily pure and rich, it is approachable.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Pinot Noir");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "New Zealand");
        initialValues.put(KEY_DESCRIPTION, "A rich and juicy wine with plum and dried strawberry character. Aromatic with beet root.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Syrah Gimblett Gravels");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "New Zealand");
        initialValues.put(KEY_DESCRIPTION, "Gimblett Gravels Syrah has aromas of warm blackberries, black cherries and cassis with hints of black pepper, licorice and tar. Medium bodied, the black berry laced palate is just a little lean in the middle with a medium level of chewy tannins and crisp acidity, finishing with medium to long persistence.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Cabernet Merlot Helmsman");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "New Zealand");
        initialValues.put(KEY_DESCRIPTION, "Displays a deep garnet-purple color and plumy, blueberry and Indian spice notes on the nose with a whiff of damp loam.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Nebbiolo Valmaggiore");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Italy");
        initialValues.put(KEY_DESCRIPTION, "Firm yet well-integrated tannins frame red cherries, sweet herbs, menthol and spices in this surprisingly large-scaled, broad Valmaggiore. Layers of fruit continue to build to the vibrant, layered finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Ornellaia");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Italy");
        initialValues.put(KEY_DESCRIPTION, "This wine is exceptionally beautiful and polished from the very first taste. Ripe, red berries, crushed flowers and deeply spiced notes are woven into an intricate fabric of indescribable class.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Rosso di Montalcino");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Italy");
        initialValues.put(KEY_DESCRIPTION, "Has a medium ruby-purple color and expressive notes of black cherries, black raspberries and mulberries over allspice, cinnamon stick and milk chocolate. Full bodied with a medium level of finely grained tannins, it has a generous amount of berry and spice flavors in the mouth nicely offset by crisp acid.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Sassicaia");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Italy");
        initialValues.put(KEY_DESCRIPTION, "What incredible aromas here with blueberries, spices, licorice, plums. Graphite too. Subtle and complex. Full and silky with a beautiful texture of fine tannins and an ultra-fine finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Scrio");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Italy");
        initialValues.put(KEY_DESCRIPTION, "Currant bush and blueberry aromas follow through to a full to medium body, with medium tannins and a chocolate, berry and vanilla bean after taste.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Tenuta Di Trinoro");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Italy");
        initialValues.put(KEY_DESCRIPTION, "Presents exotic notes of wild cherries, plums, spices, minerals and licorice all of which come together on a silky, polished frame. Smoke, incense and a host of other seductive, beguiling aromas and flavors add complexity as they wrap around the seriously intense finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Barolo Pajana");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Italy");
        initialValues.put(KEY_DESCRIPTION, "The wine shows good plushness and generosity in its dark red fruit, mocha, spices, new leather, flowers and licorice, all of which flesh out on the supple finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Barbaresco");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Italy");
        initialValues.put(KEY_DESCRIPTION, "Sweet, perfumed and totally gorgeous in its round, sensual fruit. Silky, elegant tannins frame the long, harmonious finish. ");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Brunello di Montalcino");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Italy");
        initialValues.put(KEY_DESCRIPTION, "An exotic melange of freshly cut flowers, dark cherries and plums conquers all of the senses. Intense saline notes and the wine's underlying minerality are buried under the massive fruit, but over time they emerge.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Masseto");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Italy");
        initialValues.put(KEY_DESCRIPTION, "Masseto is fabulous. Loads of black cherry, blackberry and cassis are intermingled with minerals, violets and French oak. This is an especially sensual Masseto that impresses for its clarity, intensity and length.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Solaia");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Italy");
        initialValues.put(KEY_DESCRIPTION, "Solaia saturates the palate with a heady array of super-ripe black cherries, plums, cassis, mocha and sweet French oak.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Kalimna Shiraz");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Australia");
        initialValues.put(KEY_DESCRIPTION, "Presents lovely, expressive blueberry, black raspberry and black cherries notes highlighted by violets, toast, cinnamon stick and touch of mulberries. Full-bodied and rich, the concentrated fruit is framed by medium levels of rounded tannins, crisp acid and a long, classically-styled finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Cabernet Shiraz");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Australia");
        initialValues.put(KEY_DESCRIPTION, "Displays pure cassis and black cherry cordial aromas underlying notes of aniseed, violets, pepper and chocolate. Medium to full-bodied, the palate is voluptuous and wonderfully balanced between vibrant acid and a medium level of fine tannins.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Shiraz");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Australia");
        initialValues.put(KEY_DESCRIPTION, "Shiraz is a little closed showing intense and youthful primary aromas of cassis, kirsch, cedar, prunes and a touch of mulberries. Rich, muscular, packed with flavor, it is full-bodied, concentrated and finishes long with oak still poking out.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Grange");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Australia");
        initialValues.put(KEY_DESCRIPTION, "Grange puts forward a very complex nose packed with aromas of mulberries, layers of baking spices, cloves and cinnamon with nuances of minced meat, anise, potpourri and whiffs of dried mint and chocolate.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "The Laird");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Australia");
        initialValues.put(KEY_DESCRIPTION, "The Laird is a little restrained to begin, opening out after a few moments and much coaxing to an extraordinary array of creme de cassis and black plum-based aromas with underlying chocolate box, licorice, exotic spice, oolong tea and clove hints with a touch of earthy loam.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "La Pleiade");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Australia");
        initialValues.put(KEY_DESCRIPTION, "It offers up a soaring, complex bouquet of Asian spices, incense, smoked meat, game, and blueberry. This leads to a full-bodied, voluptuous, dense, structured wine with layers of succulent fruit");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Run Rig");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Australia");
        initialValues.put(KEY_DESCRIPTION, "Saturated opaque purple/black, it has a remarkably kinky, exotic perfume of fresh asphalt, pencil lead, smoke, pepper, game, blueberry and black raspberry.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Shiraz Cabernet");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Australia");
        initialValues.put(KEY_DESCRIPTION, "An inky/purple color is accompanied by a sumptuous bouquet of apricots, honeysuckle, black raspberries, blackberries, licorice, and a hint of roasted meats.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Churchill");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Vintage Port");
        initialValues.put(KEY_DESCRIPTION, "This is very floral and fruity with dark cherries and wild berries. Full body, lightly sweet with lots of fruit and a firm tannic backbone.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Cockburn");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Vintage Port");
        initialValues.put(KEY_DESCRIPTION, "Great nose here with flowers, leaves and a wet earth character. Full body, lightly sweet with a massive finish. This is muscular, toned and intense.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Croft");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Vintage Port");
        initialValues.put(KEY_DESCRIPTION, "Beautiful aromas of violets and blueberries with hints of blue slate. Full body, medium sweet with chewy tannins and a long, long finish. A leafy, stemmy, nutty undertone to this with hints of shaved milk chocolate. Very refined and beautiful.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Dow");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Vintage Port");
        initialValues.put(KEY_DESCRIPTION, "The nose is firm quite austere with a mix of cassis and red cherry. The start of the palate feels fresh with more red fruits than black the fruit underpinned by fresh green figs.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Fonseca");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Vintage Port");
        initialValues.put(KEY_DESCRIPTION, "Very grapy and leafy with hints of spices on the nose. Full body, medium sweet with fine, chewy tannins. Powerful, long finish with nuts and shaved chocolate.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Graham");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Vintage Port");
        initialValues.put(KEY_DESCRIPTION, "A very complex and profound nose with hints of dark chocolate. Intense ripe red fruits on the palate. Firm but not aggressive tannins. Enormous body but with some fine acidity and minerality on the finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Niepoort");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Vintage Port");
        initialValues.put(KEY_DESCRIPTION, "Great nose here of crushed raspberries and minerals with hints of dark chocolate. Full body, medium sweet with a chewy, peppery and lightly stemmy character. It's long and tannic but very polished.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Quinta do Noval");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Vintage Port");
        initialValues.put(KEY_DESCRIPTION, "Fresh red fruits give a lovely brightness on the nose and although the start of the palate has the same freshness of flavour the mid palate is rich with ripe fruit lush and fleshy. There is a good mix of flavours red fruits and black fruits lots of complexity and the finish has power and depth yet fragrance.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Quinta do Vesuvio");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Vintage Port");
        initialValues.put(KEY_DESCRIPTION, "Aromas of flowers and berries with hints of dried fruits. Full body, medium sweet with solid tannins and a powerful palate.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Smith Woodhouse");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Vintage Port");
        initialValues.put(KEY_DESCRIPTION, "There is an attractive smoky quality about the nose very black fruit in character with a touch of spice, hints of cedar wood and pine needles. The rich bramble fruit is backed by liquorice and bitter chocolate a lovely complex mix and although the tannins have grip they have a seamless quality with the black fruited mix lingering on the finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Taylor");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Vintage Port");
        initialValues.put(KEY_DESCRIPTION, "A dense blue-black colour. Immediately open on the nose with the exotic perfume of violets and cassis; creamy, sweet and opulent. A waft of smoky garrigue and roasted coffee bean complement the plump ripe red berry fruits.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Warre");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Vintage Port");
        initialValues.put(KEY_DESCRIPTION, "Extremely floral and fruity with hints of lilacs and crushed fruit. Full body with refined tannins integrated with a solid core of fruit, and a rich, round texture. This is lightly sweet and follows through to a long, long finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Aalto");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Spain");
        initialValues.put(KEY_DESCRIPTION, "Focus on the nose with vanilla-tinged dark cherry and cassis fruit, followed by an undertow of candied orange peel. The palate is already displaying wonderful balance, with supple, very fine tannins and a luscious, sweet candied finish that offers blood orange and tangerine-infused dark fruit.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Contador");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Spain");
        initialValues.put(KEY_DESCRIPTION, "Beautifully balanced, with layers of ripe blackberry, creme de cassis and blueberry jam. The texture is satin-like with a generous finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        
        initialValues.put(KEY_NAME, "Vina de Andres Romeo");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Spain");
        initialValues.put(KEY_DESCRIPTION, "Raisiny, spicy, mellow. Lovely. Vigorous. ");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Clos Mogador");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Spain");
        initialValues.put(KEY_DESCRIPTION, "Asian spices, tapenade, incense, licorice, espresso, and blackberry aromas inform the nose of this structured, dense plush, powerful yet elegant offering.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Numanthia");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Spain");
        initialValues.put(KEY_DESCRIPTION, "It has a rounded, generous bouquet of raspberry coulis, wild strawberry and vanilla pod that is well-defined and not over-powering. The palate is medium-bodied with a spicy, elegant entry.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Pintia");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Spain");
        initialValues.put(KEY_DESCRIPTION, "A clear dark brown colour that looks like a mature Tokaji, with thick tears in the glass. It has a very intense, almost honeyed bouquet with allspice, singed leather, pressed rose petals, molasses, mint and a touch of dried fig.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "San Roman");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Spain");
        initialValues.put(KEY_DESCRIPTION, "It offers a very seductive bouquet with luscious red berry fruit - raspberry, wild strawberry infused with crushed violets that open with aeration. The palate is full-bodied and very well-balanced, ripe succulent tannins, plush blueberry and black cherries forming the core followed by a silky, ripe, sensual, velvety smooth finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "El Pison");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Spain");
        initialValues.put(KEY_DESCRIPTION, "It gives up an outstanding perfume of pain grille, pencil lead, damp earth, incense, and black cherry. Opulent on the palate, it is dense, rich, and full-flavored with enough ripe tannin.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Cirsion");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Spain");
        initialValues.put(KEY_DESCRIPTION, " A deep ruby/purple color precedes a smoky, barbecue spice-scented nose revealing aromas of licorice, black as well as red fruits, earth, and charcoal.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Coteaux de Languedoc Prestige Rouge");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Southern France");
        initialValues.put(KEY_DESCRIPTION, "Deep, dark and broody, there is plenty going on in the glass but mainly notes of dark cherries, chocolates and spices.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Domaine de Trevallon Rouge");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Southern France");
        initialValues.put(KEY_DESCRIPTION, "A gorgeous, minty bouquet with blackberry, a touch of blueberry, crushed violets and a little strawberry jam. It blossoms with aeration in the glass. The palate is smooth and rounded on the entry: caressing and voluminous in the mouth. The fruit is very pure with notes of strawberry.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Coteaux du Languedoc Tete de Belier");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Southern France");
        initialValues.put(KEY_DESCRIPTION, "A striking amalgam of arbor vitae and rosemary with nutmeg and cocoa powder overs above a rich reservoir of sweetly ripe black raspberry, dark cherry, and purple plum. The counterpoint of spice and sweet fruit with herbal pungency persists across this wine's polished, finely-tannic palate.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Fleur du Perigord Rouge");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Southern France");
        initialValues.put(KEY_DESCRIPTION, "There is a lovely mix of hedgerow fruits on the nose bramble, blackcurrant and raspberry. The start of the palate is fresh but it fills out in the middle lush ripe fruited with chocolate and coffee lots of complex flavours. The finish is long rich fruited with a hint of ginger.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Argentino Malbec");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Argentina");
        initialValues.put(KEY_DESCRIPTION, "It has a more opulent bouquet than the individual blends, with dark cherries, iodine, minerals and blueberry that are all beautifully defined. The palate has a dense, weighty entry with layers of ripe blackberry and boysenberry fruit laced with crushed stone and a touch of graphite.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Cabernet Sauvignon");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Argentina");
        initialValues.put(KEY_DESCRIPTION, "It has a spellbinding bouquet that exudes minerality, as if crushed stones had been sprinkled into the black fruit. With continued aeration, there are scents of oyster shell and black olive. The palate is full-bodied, with immense structure and backbone.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Malbec Catena Alta");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Argentina");
        initialValues.put(KEY_DESCRIPTION, "A bit compact, but lively, elegant and flavorful.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Cheval des Andes");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Argentina");
        initialValues.put(KEY_DESCRIPTION, "This leads to an enthralling nose of sandalwood, floral notes, exotic spices, a velvety texture, complex flavors, and an overall suave personality.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Cabernet Sauvignon");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "California");
        initialValues.put(KEY_DESCRIPTION, "Emerges from the glass with tobacco, grilled herbs, smoke, incense and plums melding together in this soft, beautifully textured wine.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Dominus");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "California");
        initialValues.put(KEY_DESCRIPTION, "It has a complex bouquet of blackberry, crushed stone, smoke and lavender that is beautifully defined and sophisticated. The palate is medium-bodied with grainy tannins.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Pinot Noir");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "California");
        initialValues.put(KEY_DESCRIPTION, "It exhibits sweet pomegranate, raspberry, cherry, roasted herb notes along with a hint of smoky oak in its seductive, round, opulent personality.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "La Muse");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "California");
        initialValues.put(KEY_DESCRIPTION, "Notions of mocha, sweet espresso roast, plum, anise, and black cherries jump from the glass. Abundant spice, subtle notes of French oak, and a full-bodied, fleshy personality.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "La Joie");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "California");
        initialValues.put(KEY_DESCRIPTION, "Exhibits a sweet, opulent bouquet of charcoal, burning embers, forest floor, blackberries and kirsch. Deep, full-bodied, supple-textured, flamboyant and extroverted with undeniable purity as well as finesse.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Harlan Estate");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "California");
        initialValues.put(KEY_DESCRIPTION, "A blend of 80% Cabernet Sauvignon, with the rest Merlot and Cabernet Franc, this enormously-endowed, profoundly rich wine must be tasted to be believed. Opaque purple-colored, it boasts spectacular, soaring aromatics of vanilla, minerals, coffee, blackberries, licorice, and cassis.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Zinfandel");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "California");
        initialValues.put(KEY_DESCRIPTION, "Boasts a sweet nose of chocolate-covered blueberries, blackberries, melted licorice, smoked herbs, and earth. This opulent, full-bodied, luscious wine conceals plenty of tannin behind its voluptuous personality.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Almaviva");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Chile");
        initialValues.put(KEY_DESCRIPTION, "A  formidable wine of exceptional richness, layers of flavor, and a multi-dimensional personality. The color is a deep saturated ruby/purple. The nose offers up juicy/jammy strawberry, black currant, and blackberry notes wrapped with subtle toasty oak.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Vinedo Chadwick");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "California");
        initialValues.put(KEY_DESCRIPTION, "It has a lovely, Margaux inspired floral bouquet with superb delineation and vigor. The palate is rounded and generous with fine tannins and excellent delineation towards the finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Anwilka");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "South Africa");
        initialValues.put(KEY_DESCRIPTION, "The nose is still very backward and giving little away at the moment, but the palate displays admirable concentration and depth with lush, rounded plumy black fruits and a firm structure.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Lourens River Valley Red");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "South Africa");
        initialValues.put(KEY_DESCRIPTION, "A blend of 25% Merlot, 53% Cabernet Franc, 21% Cabernet Sauvignon and 1% Petit Verdot. It has a more refined, herbaceous bouquet compared to other vintages whilst the palate is well structured but is a little green around the edges.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Morgenster");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "South Africa");
        initialValues.put(KEY_DESCRIPTION, "A blend of 36% Merlot, 33% Cabernet Franc, 19% Cabernet Sauvignon and 21% Petit Verdot. It has lifted blackberry, mulberry and mint on the nose with just a touch of sage and dried herbs. The palate is medium-bodied with dry tannins on the entry, a fleshy core of red cherry and wild strawberry.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Merlot");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "A range of fresh flavors such as plums, cherries, blueberries and blackberries mixed with cocoa and blackpepper tones, often dominate this type of red wine. The tannin levels are typically lower than say a Cab and the fruit flavors are typically forward - making this a prime wine candidate for consumers just getting into red wines.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Sangiovese");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Italy");
        initialValues.put(KEY_DESCRIPTION, "Typically Sangiovese grapes make medium to full-bodied wines with tannin structure ranging from medium-soft to firm. Dominate flavors associated with Sangiovese derived wines include: cherry, plum, strawberry, cinnamon and vanilla. There is often a herbaceous quality associated with Sangiovese wines. As for acidity levels, Sangiovese leans towards medium to high acidity content.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Chianti");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Italy");
        initialValues.put(KEY_DESCRIPTION, "Floral, cherry and light nutty notes are characteristic aromas with the wines expressing more notes on the mid-palate and finish than at the front of the mouth.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Port");
        initialValues.put(KEY_TYPE, "Red");
        initialValues.put(KEY_ORIGIN, "Portugal");
        initialValues.put(KEY_DESCRIPTION, "Port can be split into two distinct categories: Wood Aged or Bottle Aged. The only true bottle aged port is a Vintage Port, while the other Ports are all Wood Aged to some extent. In general, Port starts life as a red wine (unless of course it is a �white Port�) and then it's typically aged in wood casks or in the bottle");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        
        // RED
        // ======================
        // WHITE
        
        initialValues.put(KEY_NAME, "La Clarte de Haut Brion");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "Yellow fruits and dry herbs nose, supple and fleshy texture, precise and lifted acidity.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Cos d'Estournel Blanc");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "This is a very sexy wine that is full of grapefruit and pineapple flavours with a touch of cream. Rich but fresh with bright acidity.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Domaine de Chevalier Blanc");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "Pale lemon gold, dry herbs and dry honey over vibrant purity of fruit, great precision and length.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "de Fieuzal Blanc");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "Oodles of cream here with citric and pineapple freshness to balance. Ripe yet zesty.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Lynch Bages Blanc");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "Very lively white with wonderful pineapple, honey and lemons on the nose and palate.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Pape Clement Blanc");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "A white with fabulous depth and structure that shows layers of sliced apple, mineral, chalk, and mango character. It's layered and powerful like a top red. The line of acidity is wonderful.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Aile d'Argent");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "This blend of 47% Semillon and the rest Sauvignon Blanc and Sauvignon Gris offers up lush pineapple and heady melon notes with plenty of good acidity and subtle smokiness. It's a beauty.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Malartic Lagraviere Blanc");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "This full-bodied blend of 85% Sauvignon Blanc and 15% Semillon hit 14.5% natural alcohol. It is deep and rich, with loads of caramelized grapefruit, tropical fruit, subtle oak, wet stones and plenty of melons.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Monbousquet Blanc");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "At 67% Sauvignon Blanc, 33% Semillon it is very crisp, fresh and lively.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Ygrec");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Bordeaux");
        initialValues.put(KEY_DESCRIPTION, "Wonderfully complex nose in this white with ripe lemon, green papaya, lime peel and lots of ripe yellow peach. Full and focused, it offers a beautiful ripe acidity and silky texture on the palate.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Chevalier Montrachet la Cabotte");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Burgandy");
        initialValues.put(KEY_DESCRIPTION, "Precise aromas of lime and crushed stone. More reserved and opulent than the Chevalier today but purer and higher-pitched, boasting outstanding precision to its citrus and floral flavors. Really superb energy here.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Chevalier Montrachet Demoiselles");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Burgandy");
        initialValues.put(KEY_DESCRIPTION, "Bright pale yellow. Rich aromas and flavors of lemon zest, white flowers and gingerbread. Full and sappy but given an urgent, juicy quality by a superb acid/mineral spine.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Corton Charlemagne");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Burgandy");
        initialValues.put(KEY_DESCRIPTION, "Bright yellow. Sexy, exotic high-toned aromas of candied peach, marmalade and pineapple liqueur; smells a bit like a riesling vendange tardive.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Macon Pierreclos Chavigne Blanc");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Burgandy");
        initialValues.put(KEY_DESCRIPTION, "Good pale yellow. Pure, scented nose offers aromas of lime, pineapple, acacia flower and powdered stone. Sappy, spicy and dense with extract, boasting lovely definition and a pronounced mineral character.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Meursault Les Clous");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Burgandy");
        initialValues.put(KEY_DESCRIPTION, "The energetic and intensely mineral-driven middle weight flavors possess excellent depth on the explosive, balanced and admirably persistent finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Meursault Genevrires");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Burgandy");
        initialValues.put(KEY_DESCRIPTION, "Good pale yellow. Enticing aromas of peach, pear and white flowers. At once juicy and creamy, with an aromatic hazelnut quality adding interest to the ripe stone fruit flavors. Tactile and expansive wine with a restrained sweetness and excellent saline length.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Montrachet");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Burgandy");
        initialValues.put(KEY_DESCRIPTION, "Good pale yellow. Deeply pitched aromas of apricot, musky truffle and smoky oak. Fat and sweet following the Cabottes--in fact downright exotic.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Pouilly Fuisse");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Burgandy");
        initialValues.put(KEY_DESCRIPTION, "Appley and a little tight.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Puligny Combettes");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Burgandy");
        initialValues.put(KEY_DESCRIPTION, "Pale bright yellow. Ripe aromas of apricot and mirabelle; less minerally and more earthy than the Meursault Perrieres. Fatter and sweeter on the palate too, but shows a slightly herbal edge.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Puligny Folatieres");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Burgandy");
        initialValues.put(KEY_DESCRIPTION, "Sexy, exotic high-toned aromas of candied peach, marmalade and pineapple liqueur; smells a bit like a riesling vendange tardive.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Batard Montrachet");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Burgandy");
        initialValues.put(KEY_DESCRIPTION, "A pretty and densely fruited nose of white peach, pear, acacia blossom and background hints of citrus and toast.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Bourgogne Aligote");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Burgandy");
        initialValues.put(KEY_DESCRIPTION, "Big, bold and powerful with imposingly-scaled flavors that coat the palate with dry extract before terminating in a massively long and borderline painfully intense finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Chassagne Caillerets");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Burgandy");
        initialValues.put(KEY_DESCRIPTION, "There is a plush quality to the rich and generous flavors that possess a suave mouth feel as well as a delicious, dry and energetic finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Ermitage L'Oree");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Rhone");
        initialValues.put(KEY_DESCRIPTION, "An intoxicating mix of white flower, mango, peach, poached pear and Asian spice notes merges into rich, full, unusually forward and impressively concentrated flavors that possess serious muscle, size and weight on the powerfully long finish that possesses better acidity than one expects from the fantastically rich mid-palate.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Saint Joseph Granits Blanc");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Rhone");
        initialValues.put(KEY_DESCRIPTION, "An ultra pure nose of ripe but austere green fruit brimming with oyster shell and seawater notes that introduce elegant, pure and sweet flavors all wrapped in a beautifully balanced and wonderfully detailed finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Condrieu");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Rhone");
        initialValues.put(KEY_DESCRIPTION, "Boasting stunning minerality in addition to copious notes of honeysuckle, lychee nut and mango.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Hermitage Blanc");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Rhone");
        initialValues.put(KEY_DESCRIPTION, "Normally a blend of 80% Marsanne and 20% Roussanne, exhibits layers of concentration, superb acidity and minerality, laser-like focus, and plenty of quince, white currant, citrus oil, honeysuckle, anise, white peach and apricot marmalade notes.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Chateau-Grillet");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Rhone");
        initialValues.put(KEY_DESCRIPTION, "Exhibits notes of lychee nuts, white peaches and honeysuckle, good minerality and a fresh, lively, concentrated style with good acidity as well as vibrancy.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Andre Clouet");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "A richly-textured, expansive Champagne, the Andre Clouet bursts from the glass with expressive layers of perfumed Pinot fruit. The wine possesses stunning depth, clarity and precision in an utterly engaging style, with never-ending waves of fruit that caress the palate from start to finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Billiot Cuvee");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "A distinctive brut, evoking wet wool, beeswax and malt flavors. It's harmonious and finely textured, with a finish reminiscent of hazelnut. Dry, this makes an ideal aperitif but will also match light foods.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Bollinger Special Cuvee");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "Complex aromas and flavors of rose, grilled nuts, citrus and a gently oxidative note course through this muscular, full-bodied bubbly.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Krug Grande Cuvee");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "A powerful, well-toned Champagne, featuring coconut, toast, grapefruit and coffee aromas and flavors. There are plenty of bass notes, punctuated by crisp acidity, and it all comes together on the fine texture, with aftertaste of roasted hazelnut and coffee.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Moet Ice");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "Very fine mousse. Sweetly spicy, soil-inflected aromas of nutmeg, nut oil, toasted biscuit, smoke and minerals, with the ineffable yeast autolysis character of the greatest Champagnes.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Pol Roger ");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "This is lovely, framing its baking bread, honey and citrus flavors with an elegant, focused presentation. Turns a little crisp on the finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Ruinart Blanc de Blancs");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "A strong whiff of graphite turns quickly to vanilla and coffee flavors on the palate, with a candied citrus accent. Delicate and detailed, with a firmness on the lingering aftertaste of vanilla and lemon.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Ruinart Brut");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "Lovely harmony. This starts with an explosive nose of toasted brioche, mineral and honey, adding a citrus element on the palate, all in a firm, full-bodied package. Lingering aftertaste of grilled hazelnuts.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Ville de la Reine");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "Sparkling bright lemon and showing a steady stream of fine bubbles in the glass, this delightfully elegant champagne combines subtle complexity with an evident ripeness and depth to the high quality fruit. ");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Cristal");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "Rich, indulgent, baked character on the nose. Long, toasty palate. Certain minty freshness.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Dom Perignon");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "An intense toasty and smoky nose with focussed minerality, zesty citrus fruits and a hint of spicy grapefruit. Wonderfully fresh and 'zingy' on the palate with clean cut acidity offsetting sumptuous and generous ripe white fruits with an attractive slate-like mineral character. ");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Veuve Clicquot");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "Reductive, smoky nose with tight lemony fruit and a hint of citrus peel. Beautifully balanced.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Krug");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "A rich, intense nose of fresh bread and biscuits. The palate has clean, citrus fruits with hints of smoke and toasted oak. This is fresh and racy, with precise minerality and zesty lemon fruit. Well balanced, there is good tension between the fruit and the acidity. ");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Salon le Mesnil");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "There's a streak of minerality and a lacy texture to this floral Champagne, with finely tuned acidity to focus the candied lemon zest, poached pear, nutmeg and ginger hints. A more delicate style, but seamless in its balance and finesse, with a subtle finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Selosse Millesime");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "More florally scented with aromas of honeysuckle and buttered croissant with peach conserve. Very crisp and tight on the palate. Long, minerally finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Sauvignon Blanc");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "New Zealand");
        initialValues.put(KEY_DESCRIPTION, "Opens with intense grapefruit, passion fruit and green mango notes and touches of pineapple and fresh sage. Medium-bodied with a fair amount of viscosity to the texture, it has lively acid and a long finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Chardonnay");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "New Zealand");
        initialValues.put(KEY_DESCRIPTION, "An aromatic wine with mineral and spice and sliced apple character. Full and medium sweet. Intense finish. ");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Riesling Bannockburn");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "New Zealand");
        initialValues.put(KEY_DESCRIPTION, "Riesling brings intensely perfumed, acacia honey, lemon curd, lime cordial and jasmine notes to the glass. This light body, low alcohol wine (9.5% declared) is a medium sweet style with excellent concentration enlivened by racy acid.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Viognier");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "New Zealand");
        initialValues.put(KEY_DESCRIPTION, "It gives some cedary, oak-led aromas at this stage over expressive notes of ripe peaches, fresh apricots and apple tart. The rich, full bodied, satin textured palate gives some sweetness offset by crisp acidity, finishing long and toasty.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Graacher Himmelreich Spatlese");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Germany");
        initialValues.put(KEY_DESCRIPTION, "Melon, mango and lemongrass dominate the bouquet. Sweet and succulent in the mouth, offering mandarin orange, cherry pit and subtle slate flavors. Still a touch austere, but animated on the finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Oberhauser Brucke Auslese Goldcap");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Germany");
        initialValues.put(KEY_DESCRIPTION, "Mango and papaya, quince and peach deliver an impression both tropical and northerly, with musky animal and smoky black tea scents adding intrigue.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Scharzhofberger Auslese Fuder");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Germany");
        initialValues.put(KEY_DESCRIPTION, "Golden yellow. Enthralling aroma of candied peach, lemon oil and acacia honey, with a hint of brown spice botrytis. The lusciously sweet tropical fruit palate maintains a remarkable delicacy in spite of its richness and depth.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Kiedricher Grafenberg");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Germany");
        initialValues.put(KEY_DESCRIPTION, "Rich golden yellow. Exuberant aromas of caramelized peach, quince preserves and oyster shell on a bed of honeyed botrytis. Incredibly dense, creamy richness is paired with a juicy, almost salty tanginess. ");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Niederhauser Hermannshohle Auslese Goldkap");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Germany");
        initialValues.put(KEY_DESCRIPTION, "Smoky black tea wafts over quince preserves and almost overripe pear on the nose and the glossy, subtle creamy palate. Nut paste and a savory meat dimension contribute complexity to this luscious lingering Auslese.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Brauneberger Juffer Sonnenuhr Beerenauslese");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Germany");
        initialValues.put(KEY_DESCRIPTION, "Deep golden yellow. Expressive aromas of candied pineapple, acacia honey and pine nuts in a rich bed of brown-spice botrytis. Baked apricot and glazed honey flavors are kept vibrant by saline minerality.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Riesling");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Alsace");
        initialValues.put(KEY_DESCRIPTION, "A stunning, haunting nose of rowan, linden, iris, licorice, resin, white peach, and sea breeze foreshadows the elusive complexities of a palate combining sumptuous richness and subtle oiliness with lift and refreshment.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Pinot Gris Clos Windsbuhl");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Alsace");
        initialValues.put(KEY_DESCRIPTION, "Smoky black tea, peat, and grilled peach on the nose follow on a voluminous, oily palate, along with saline, chalky notes surprising in a wine this overwhelmingly ripe, and helping to convey some counterpoint in a finish that - almost miraculously - is heat-free.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Que Bonito Cacareaba");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Spain");
        initialValues.put(KEY_DESCRIPTION, "Pale golden yellow.Delicate aromas of yellow plum, persimmon and acacia blossom. Offers a honeyed richness on top of a firm mineral foundation, with a salty crispness keeping the palate fresh.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Gewurztraminer");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Germany");
        initialValues.put(KEY_DESCRIPTION, "Pale golden-yellow color. Fresh, nuanced nose combines seared pineapple, mango, orange marmalade, peach, apricot, clove and caraway seed.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Sancerre Chavignol Les Monts Damnes");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Loire");
        initialValues.put(KEY_DESCRIPTION, "The nose is highly perfumed and intense, with classic notes of cut grass, limes and the tell-tell hint of flint.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Pouilly Fume Silex");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Loire");
        initialValues.put(KEY_DESCRIPTION, "Delivers an almost inordinate diversity of floral, herbal, citrus (predominately grapefruit), and pit-fruit (predominately nectarine) elements, with the bitterness of fruit pits, smoky pungency of red currant and crushed stone.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Jurancon");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Loire");
        initialValues.put(KEY_DESCRIPTION, "On the palate this exhibits amazing, chalky density, firm citricity and a satiny, slippery, glycerin-rich texture.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Honivogl Gruner Veltliner Smaragd");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Austria");
        initialValues.put(KEY_DESCRIPTION, "Winter pear, raw hazelnut, beetroot, and green bean are suffused with stone and salt and wreathed in subtle, bittersweet floral perfume. ");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Sine Qua ");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "California");
        initialValues.put(KEY_DESCRIPTION, "Possesses nervy, vibrant acidity that is hard to imagine in a wine of this mass and richness.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Sine Qua ");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "California");
        initialValues.put(KEY_DESCRIPTION, "Possesses nervy, vibrant acidity that is hard to imagine in a wine of this mass and richness.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Straw Man");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "California");
        initialValues.put(KEY_DESCRIPTION, "It is a wine of extraordinary richness, honeyed complexity, and a finish that lasts over 60 seconds.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
		initialValues.put(KEY_NAME, "Chardonnay");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "France");
        initialValues.put(KEY_DESCRIPTION, "Chardonnay is the most common and well known variety of white grape in wine production today. The most prominent flavor in Chardonnay wine comes from oak used in the wine production process. Other flavors and aromas include fruit, lemon, melon, grass, and vanilla. Some other characteristics of Chardonnay wines are high acidity, golden colors, and a velvety feel in the mouth.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Moscato");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Italy");
        initialValues.put(KEY_DESCRIPTION, "Moscato tends to be a popular white wine among new wine lovers and enjoys a significant following with seasoned wine enthusiasts who enjoy a lighter-styled wine with brunch, dessert or on its own as a capable aperitif.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Viognier");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "France");
        initialValues.put(KEY_DESCRIPTION, "Viognier wines are well known for their floral aromas, due to terpenes, which are also found in Muscat and Riesling wines. There are also many other powerful flower and fruit aromas which can be perceived in these wines depending on where they were grown, the weather conditions and how old the vines were.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Pedro Ximenez");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Spain");
        initialValues.put(KEY_DESCRIPTION, "PX is made by drying the grapes under the hot Spanish sun, concentrating the sweetness, which are then used to create a thick, black liquid with a strong taste of raisins and molasses that is fortified and aged in solera.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();

        initialValues.put(KEY_NAME, "Vidal Blanc");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "France");
        initialValues.put(KEY_DESCRIPTION, "The wine produced from Vidal blanc tends to be very fruity, with aroma notes of grapefruit and pineapple. Due to its high acidity and sugar potential it is particularly suited to sweeter, dessert wines.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Sherry");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Spain");
        initialValues.put(KEY_DESCRIPTION, "Sherry is produced in a variety of dry styles made primarily from the Palomino grape, ranging from light versions similar to white table wines, such as Manzanilla and Fino, to darker and heavier versions that have been allowed to oxidise as they age in barrel, such as Amontillado and Oloroso.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Almond Champagne");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "This white sparkling wine is fermented naturally in the tradtional Champagne method with just a hint of pure almond added to give it a creamy, nutty almost sweet taste.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Symphony");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "California");
        initialValues.put(KEY_DESCRIPTION, "It is slightly dry and an anomaly of a moderately strong white of 12% alcohol with a light addictive taste of grapefruit and wildflowers.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Chenin Blanc");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "France");
        initialValues.put(KEY_DESCRIPTION, " This white wine can range from dry to very sweet depending on the time of harvest, producing flavors that vary from apple, melon, lime and pear with hints of vanilla and honey.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        initialValues.put(KEY_NAME, "Scheurebe");
        initialValues.put(KEY_TYPE, "White");
        initialValues.put(KEY_ORIGIN, "Germany");
        initialValues.put(KEY_DESCRIPTION, "Full body, pronounced acidity and a bouquet and taste reminiscent of black currants.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		// WHITE
		// ===========================
		// ROSE
		
		initialValues.put(KEY_NAME, "Marsannay Rose");
        initialValues.put(KEY_TYPE, "Rose");
        initialValues.put(KEY_ORIGIN, "Burgundy");
        initialValues.put(KEY_DESCRIPTION, "Pale salmon. Much more refreshing on the nose than most. Real wine on the palate. Fizz with personality.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Bollinger Rose");
        initialValues.put(KEY_TYPE, "Rose");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Egly Ouriet Rose");
        initialValues.put(KEY_TYPE, "Rose");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "There is superb material in the density of fruit, along with perfumed aromatics and silky, chalky tannins that frame the long finish. It's all here in this sublime, sensual wine.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Krug Rose");
        initialValues.put(KEY_TYPE, "Rose");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "Mid salmon. Definitely darker than it used to be. Very light rhubarb nose. You have to struggle to get that aroma! Very nice tight bead - superior texture without a doubt but low-key flavour. Tightly packed. Very fresh. ");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Moet Brut Imperial Rose");
        initialValues.put(KEY_TYPE, "Rose");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "The aromas are delicate, but this gathers intensity on the palate, revealing smoke, toast, strawberry and cherry flavors. It builds to a rich finish, with a lingering aftertaste of cherry and mineral.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Ruinart Rose");
        initialValues.put(KEY_TYPE, "Rose");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "This rose offers the essence of whole-grain bread, dried flowers, berries and spices. It's elegant, intense and supported by a lively acidity. Fine length and harmony. Overall, complex and inviting. ");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Veuve Clicquot Rose");
        initialValues.put(KEY_TYPE, "Rose");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "This combines finesse and intensity, expressing its spice, biscuit, berry and honey flavours on a firm, dense structure. There's a wonderful contrast of maturity and freshness, and it just explodes on the finish.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Cristal Rose");
        initialValues.put(KEY_TYPE, "Rose");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "Rich, round and seductive in this vintage. Warm and resonant, the Cristal Rose blossoms in all directions with gorgeous aromatics and expressive, generous fruit. ");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Pol Roger Rose");
        initialValues.put(KEY_TYPE, "Rose");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "Quite fruity and vinous at first but then the minerality kicks in, giving the wine its energy and sense of focus. Crushed flowers, spices and mint unfold in the glass, adding complexity and nuance. ");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Dom Perignon Rose");
        initialValues.put(KEY_TYPE, "Rose");
        initialValues.put(KEY_ORIGIN, "Champagne");
        initialValues.put(KEY_DESCRIPTION, "Explodes from the glass with endless layers of huge, voluptuous fruit. A big, full-bodied wine.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
		initialValues.put(KEY_NAME, "Nyetimber Rose");
        initialValues.put(KEY_TYPE, "Rose");
        initialValues.put(KEY_ORIGIN, "England");
        initialValues.put(KEY_DESCRIPTION, "Blossoms in all directions with gorgeous aromatics and expressive, generous fruit.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
		
        initialValues.put(KEY_NAME, "White Zinfandel");
        initialValues.put(KEY_TYPE, "Rose");
        initialValues.put(KEY_ORIGIN, "California");
        initialValues.put(KEY_DESCRIPTION, "White Zinfandel, often abbreviated as White Zin, is an off-dry to sweet, pink-colored blush wine. White Zinfandel is made from the Zinfandel wine grape, which would otherwise produce a bold and spicy red wine. As such, it is not a grape variety but a method of processing Zinfandel grapes.");
        initialValues.put(KEY_FAVORITES, 0);
        initialValues.put(KEY_WISH, 0);
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.clear();
        
        
        
		// ROSE

    }
}