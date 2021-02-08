# Utils

[![](https://jitpack.io/v/SparshInnovators/AndroidUtils.svg)](https://jitpack.io/#SparshInnovators/AndroidUtils)

This library has utility functions, that are helpful while android developments

**Step 1. Add the JitPack repository to your build file**

Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```


**Step 2. Add the dependency**
```
dependencies
  {
      implementation 'com.github.SparshInnovators:AndroidUtils:1.0.0'
  }
```


## Functions

**1. Show SnackBar**
This function can be called for showing Snackbar in the application.
```
SparshUtils().showSnackBar(Activity, CoordinatorLayout, Message, Duration) 
```

**2. Show Toast**
This function can be called for showing Toast in the application.
```
SparshUtils().showToast(Activity, Message, Duration)
```

**3. Check for Internet Availability**
This function is used to check the availability of the internet connection.
```
SparshUtils().isInternetAvailable(context: Context): Boolean
```

**4. Get IP address**
This function is used to show get the IP Address.
```
SparshUtils().getWifiIP(applicationContext: Context): String
```


**5. Create DataStore in the device**
This function can be called before storing any data in datastorage. It should be called only once for a datastore name. It is used for instantiating DataStore and creating a container in the **dataStoreName** provided in the parameter.
```
SparshUtils().createDataStoreWithName(context: Context, dataStoreName: String)
```

**6. Save value to Data Store**
This function will save a value to the data storage, the **key** should be unique for different value. Later you can fetch the value with its key.
```
SparshUtils().setData(key: String, value: Boolean)
```
**More Functions**
```
- setData(key:String, value:String)

- setData(key:String, value:Boolean)

- setData(key:String, value:Int)
```


**7. Get value from Data Store**
This function will get the value from the data storage, the **key** should be unique for different value.
```
SparshUtils().getData(key: String, default: String): String?
```
**More Functions**
```
- getData(key: String, default: String): String?

- getData(key: String, default: Boolean): Boolean?

- getData(key: String, default: Int): Int?
```

**8. Validate Email**
This function is used to validate the email.
```
SparshUtils().validateEmail(email: CharSequence): Boolean
```

**9. Validate Password**
This function is used to validate the password. 
```
SparshUtils().validatePassword(activity: Activity, password: CharSequence, textLength: Int): Boolean 
```

**10. Bitmap from View**
This function will return a bitmap from any view.
```
SparshUtils().getBitmapFromView(view: View): Bitmap?
```

**11. Check string for alphanumeric**
This function will return a bitmap from any view.
```
SparshUtils().isAlphaNumeric(target: String?): Boolean
```

**12. Hide Keyboard**
This function is used to hide the keyboard.
```
SparshUtils().hideKeyBoard(context: Context, v: View)
```

**13. Get Bold Typeface**
This function is used to hide the keyboard.
```
SparshUtils().getBold(c: Context, path: String): Typeface?
```

**14. Get Regular Typeface**
This function is used to hide the keyboard.
```
SparshUtils().getRegular(c: Context, path: String): Typeface?
```

**15. Get Medium Typeface**
This function is used to hide the keyboard.
```
SparshUtils().getMedium(c: Context, path: String): Typeface?
```

**16. Truncate to two decimal places**
This function is used to truncate numbers upto 2 decimal places.
```
SparshUtils().truncateUptoTwoDecimal(doubleValue: String): String?
```

**17. Get Application Version Code**
This function is used to get the application version code.
```
SparshUtils().getAppVersionCode(c: Context): Int 
```

**18. Parse time from one format to another**
This function is used to get the application version code.
```
SparshUtils().parseTime(time: String?, pattern: String?): Date?
```

**19. Parse time from one format to another**
This function is used to get the application version code.
```
SparshUtils().parseTime(time: String?, pattern: String?): Date?
```












