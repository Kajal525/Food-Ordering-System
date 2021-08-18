package com.example.collagefinalproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.collagefinalproject.model.Cart;
import com.example.collagefinalproject.model.Category;
import com.example.collagefinalproject.model.Product;
import com.example.collagefinalproject.model.User;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_USER = "user_table";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    //Table Category
    private static final String TABLE_CATEGORY = "category_table";
    private static final String COLUMN_CATEGORY_ID = "category_id";
    private static final String COLUMN_CATEGORY_NAME = "category_name";


    //Table product
    private static final String TABLE_PRODUCT = "product_table";

    private static final String COLUMN_PRODUCT_ID = "product_id";
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_PRODUCT_PRICE = "product_price";
    private static final String COLUMN_PRODUCT_DESC = "product_desc";



    //Table Cart
    private static final String TABLE_CART = "cart_table";

    private static final String COLUMN_CART_PRODUCT_NAME = "cart_product_name";
    private static final String COLUMN_CART_PRODUCT_ID = "cart_product_id";
    private static final String COLUMN_CART_PRODUCT_PRICE = "cart_product_price";
    private static final String COLUMN_CART_PRODUCT_QTY = "cart_product_qty";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // create table sql query
    private String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
            + COLUMN_CATEGORY_ID + " INTEGER," + COLUMN_CATEGORY_NAME + " TEXT" + ")";

    private String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
            + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CATEGORY_ID + " INTEGER,"
            + COLUMN_PRODUCT_NAME + " TEXT," +
            COLUMN_PRODUCT_PRICE + " TEXT," +
            COLUMN_PRODUCT_DESC + " TEXT" +
            ")";
    private String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
            + COLUMN_CART_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CART_PRODUCT_NAME + " TEXT,"
            + COLUMN_CART_PRODUCT_PRICE + " TEXT," +
            COLUMN_CART_PRODUCT_QTY + " TEXT" +
            ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_CATEGORY_TABLE = "DROP TABLE IF EXISTS " + TABLE_CATEGORY;
    private final String DROP_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + TABLE_PRODUCT;
    private final String DROP_CART_TABLE = "DROP TABLE IF EXISTS " + TABLE_CART;

    /**
     * Constructor
     *
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_CART_TABLE);


        db.execSQL("DELETE from category_table");
        db.execSQL("INSERT INTO category_table (category_id,category_name) VALUES (1,'Vegetarian')");
        db.execSQL("INSERT INTO category_table (category_id,category_name) VALUES (2,'Chicken')");
        db.execSQL("INSERT INTO category_table (category_id,category_name) VALUES (3,'Sandwich')");
        db.execSQL("INSERT INTO category_table (category_id,category_name) VALUES (4,'Dessert')");
        db.execSQL("INSERT INTO category_table (category_id,category_name) VALUES (5,'Burgers')");
        db.execSQL("INSERT INTO category_table (category_id,category_name) VALUES (6,'Indian')");

        db.execSQL("DELETE from product_table");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (1,'Samosa ( 2 Pcs)','3.95','Samosa served with chutney. One order comes with two pieces.')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (1,'onion bhajia','5.49','Onion in a spicy chickpea batter, deep fried.')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (1,'mixed platter','8.95','vegetables pakpras , samosa , onion bhajia , all tikka and papadum served with chana and tamarind sauce')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (1,'Veggie Dumplings (6 pcs)','6','Vegetarian. Six deep-fried vegetable dumplings.')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (1,'Spring Rolls (6 pcs)','6','Vegetarian. Six crispy pastry rolled with glass noodles and vegetables with plum sauce.')");

        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (2,'Chicken Pakora','9.95','Juice chicken fillet batter in gram flour and fried.')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (2,'Tandoori Chicken','11','')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (2,'Chicken Mirch Tikka','14.99','')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (2,'Chilli Chicken','11.99','')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (2,'Chicken Malai Kebab','12.99','')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (2,'Deep-Fried Chicken Wings','9.50','Deep-Fried Chicken Wings')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (2,'Chicken Noodle Soup','3.6','Chicken Noodle Soup')");


        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (3,'Chicken Sandwich','8.59','Sandwich includes ranch, spicy or mild mayonnaise, tomato, lettuce, and onion all stacked inside freshly baked diamond buns.')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (3,'Shrimp Sandwich','8.59','Sandwich includes ranch, spicy or mild mayonnaise, tomato, lettuce, and onion all stacked inside freshly baked diamond buns.')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (3,'Fish Sandwich Combo','9','Sandwich includes ranch, spicy or mild mayonnaise, tomato, lettuce, and onion all stacked inside freshly baked diamond buns.')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (3,'Big Harv','10','Two Original Patties, Classic and Swiss style cheese served on a poppy seeded premium bun and topped the way you want it (700 Cals)')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (3,'Great Canadian Club Sub','13.89','This sandwich favourite is made with turkey and pork raised on Canadian farms. It comes with turkey, ham, bacon, lettuce, tomato, and a smoky honey mustard sauce all on our new Italian bread.')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (3,'Tuna Sub','14.19','You’ll love every bite of our classic tuna sandwich. 100% wild caught tuna blended with creamy mayonnaise then topped with your choice of crisp, fresh veggies. 100% delicious. Includes a Sub, Chips, or 2 Cookies, and Bottled Drink')");


        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (4,'Homemade Apple Strudel','6.95','Homemade Apple Strudel')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (4,'Palatschika Crepe','8.95','Palatchika crepe with nutella')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (4,'Strawberry Banana Smoothie','8.67','Strawberry and banana puree blended with ice.')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (4,'Hot Fudge Sundae','7.7','Hot Fudge Sundae')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (4,'A Sweet Treat','21.05','Belgian waffle topped with brownies, Belgian white chocolate, and a side of vanilla ice cream')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (4,'My Crepe Ate Your Brownie','21.61','D Spot Desserts moist brownie wrapped in a crepe, topped with Belgian milk, dark and whtie chocolate served with a side of vanilla ice cream.')");



        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (5,'Wing Combo','8.49','Five wings served with fries and pop')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (5,'Double Teen Burger','15.99','Two 100% pure beef patties topped with cheddar cheese, bacon, lettuce, tomato, pickles, ketchup, mustard, and Teen® sauce, served on a freshly toasted bun. (670 Cals)')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (5,'Original Burger Combo','13.47','Juicy, flame-grilled burger made with 100% Canadian beef, placed on a fresh, lightly toasted bun and topped just the way you want it. Served with your choice of a side and a drink. (400-1120 Cals)')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (5,'Angus Burger Combo','14','Made with premium Canadian Angus beef and grilled over an open flame, Harvey’s quarter pound Angus Burger is sure to make your taste buds happy. Served with your choice of a side and a drink.')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (5,'Grilled Chicken Sandwich','12.59','Whole white meat chicken breast flame-grilled to perfection and served on a fresh, lightly toasted bun of your choice - regular or multigrain. Served with your choice of a side and a drink. (330-1050 Cals)')");


        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (6,'Plain Dosa','8.5','Thin rice and lentils crepe.')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (6,'Pav Bhaji Dosa','10.9','Thin rice and lentils crepe, stuffed with pav bhaji curry.')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (6,'Pav Bhaji','8','Crushed sauteed mixed vegetables in a delicious tomato and garlic based sauce served with two buttered griddled buns.')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (6,'Chole Bhature','8.5','Savoury curried chickpeas served with flash fried bread.')");
        db.execSQL("INSERT INTO product_table (category_id,product_name,product_price,product_desc) VALUES (6,'Samosa Chaat','8','Samosas topped with yogurt, onions, special chutneys, and garnished with sev and coriander.')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_CATEGORY_TABLE);
        db.execSQL(DROP_PRODUCT_TABLE);
        db.execSQL(DROP_CART_TABLE);
        // Create tables again
        onCreate(db);
    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, category.getName());
        // Inserting Row
        db.insert(TABLE_CATEGORY, null, values);
        db.close();
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, product.getName());
        values.put(COLUMN_PRODUCT_PRICE, product.getPrice());
        values.put(COLUMN_PRODUCT_DESC, product.getDesc());
        values.put(COLUMN_CATEGORY_ID, product.getCategoryId());
        // Inserting Row
        db.insert(TABLE_PRODUCT, null, values);
        db.close();
    }

    public void addToCart(Cart cart) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CART_PRODUCT_ID, cart.getId());
        values.put(COLUMN_CART_PRODUCT_NAME, cart.getName());
        values.put(COLUMN_CART_PRODUCT_PRICE, cart.getPrice());
        values.put(COLUMN_CART_PRODUCT_QTY, cart.getQty());
        // Inserting Row
        db.insert(TABLE_CART, null, values);
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {email, password};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public User getUser(String Email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{Email});
        User user = new User();
        // Read data, I simplify cursor in one line
        if (cursor.moveToFirst()) {
            user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
            user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
        }
        cursor.close();
        db.close();
        return user;
    }

    public ArrayList<Category> getAllCategory() {
        ArrayList<Category> tags = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Category t = new Category();
                t.setId(c.getInt((c.getColumnIndex(COLUMN_CATEGORY_ID))));
                t.setName(c.getString(c.getColumnIndex(COLUMN_CATEGORY_NAME)));
                tags.add(t);
            } while (c.moveToNext());
        }
        return tags;
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Product t = new Product();
                t.setId(c.getInt((c.getColumnIndex(COLUMN_PRODUCT_ID))));
                t.setCategoryId(c.getInt(c.getColumnIndex(COLUMN_CATEGORY_ID)));
                t.setName(c.getString(c.getColumnIndex(COLUMN_PRODUCT_NAME)));
                t.setPrice(c.getString(c.getColumnIndex(COLUMN_PRODUCT_PRICE)));
                products.add(t);
            } while (c.moveToNext());
        }
        return products;
    }

    public Product getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + COLUMN_PRODUCT_ID + " ="+id;
        Cursor cursor = db.rawQuery(sql,null);

        Product  product = new Product();
        // Read data, I simplify cursor in one line
        if (cursor.moveToFirst()) {
            product.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID))));
            product.setCategoryId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_ID))));
            product.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME)));
            product.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_PRICE)));
            product.setDesc(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_DESC)));
        }
        cursor.close();
        db.close();
        return product;
    }


    public ArrayList<Cart> getAllCartItems() {
        ArrayList<Cart> tags = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CART;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Cart t = new Cart();
                t.setName(c.getString((c.getColumnIndex(COLUMN_CART_PRODUCT_NAME))));
                t.setId(c.getInt(c.getColumnIndex(COLUMN_CART_PRODUCT_ID)));
                t.setPrice(c.getString(c.getColumnIndex(COLUMN_CART_PRODUCT_PRICE)));
                t.setQty(c.getInt(c.getColumnIndex(COLUMN_CART_PRODUCT_QTY)));
                tags.add(t);
            } while (c.moveToNext());
        }
        return tags;
    }

    public ArrayList<Product> getAllProductsById(int id) {
        ArrayList<Product> tags = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT + " WHERE " + COLUMN_CATEGORY_ID + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Product t = new Product();
                t.setId(c.getInt((c.getColumnIndex(COLUMN_PRODUCT_ID))));
                t.setCategoryId(c.getInt(c.getColumnIndex(COLUMN_CATEGORY_ID)));
                t.setName(c.getString(c.getColumnIndex(COLUMN_PRODUCT_NAME)));
                t.setPrice(c.getString(c.getColumnIndex(COLUMN_PRODUCT_PRICE)));
                tags.add(t);
            } while (c.moveToNext());
        }
        return tags;
    }

    public void deleteCategory(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, COLUMN_CATEGORY_ID + " =?", new String[]{String.valueOf(id)});
        if (id != null) {
            db.delete(TABLE_PRODUCT, COLUMN_CATEGORY_ID + " =?", new String[]{String.valueOf(id)});
        }
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, COLUMN_PRODUCT_ID + " =?", new String[]{String.valueOf(id)});
    }

    public void deleteCart(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, COLUMN_CART_PRODUCT_ID + " =?", new String[]{String.valueOf(id)});

    }

    public void deleteCartAllItem() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_CART, null, null);
        sqLiteDatabase.close();
    }

    public void updateCart(int id, int qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CART_PRODUCT_QTY, qty);
        db.update(TABLE_CART, values, COLUMN_CART_PRODUCT_ID + " =?", new String[]{String.valueOf(id)});
        db.close();
    }
}