# MVPWithFirebase
![MVP banner](https://github.com/tmaxxdd/MVPWithFirebase/blob/master/github_banner.png)

This app is a result of many great examples and tutorials about MVP that I have found on the internet. 
MVP is one of the simplest approach for implementing app's architecture. 
MVP stands for M(Model) V(View) P(Presenter). 
MVPWithFirebase also as name points is based on Firebase database by Google.

## View
Views are based on fragments. It means that is only **one registered Activity** and this is **MainActivity**.

###  manifest.xml
Thus in manifest you don't have to care about other classes which may use lifecycle.
```xml
<activity android:name=".ui.main.MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
```

### MainActivity
MainActivity inherits from FOABaseActivity which is the main manager for all fragments.
This approach is called Fragment Oriented Application and is suggested while making app with many windows.
```kotlin
class MainActivity : FOABaseActivity(), MainContract, BaseFragmentInteractionListener {
    private val TAG = javaClass.simpleName

    ...

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)

        ...
    }
```
