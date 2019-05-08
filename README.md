# MVPWithFirebase
![MVP banner](https://github.com/tmaxxdd/MVPWithFirebase/blob/master/github_banner.png)

This app is a result of many great examples and tutorials about MVP that I have found on the internet. 
MVP is one of the simplest approach for implementing app's architecture. 
MVP stands for M(Model) V(View) P(Presenter). 
MVPWithFirebase also as name points is based on Firebase database by Google.

In my opinion the best understanding of MVP concept gives Antonio Leiva and it is in Kotlin :smiley:
https://antonioleiva.com/mvp-android/

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

### Structure
App is designed to show data in three steps. Firstly it shows cards with general topics, nextly content page where user can choose the most detailed data.
> TOPIC -> CONTENT -> DETAIL

### Topic
Main view is represented by TopicsFragment
<img src="https://github.com/tmaxxdd/MVPWithFirebase/blob/master/screenshot_topic.png" align="left" width="200">

#### Topic's contract
This view only shows cards so it's contract is also very simple.
```kotlin
interface TopicsContract {

    // View
    interface Fragment : IBaseFragment {

        // Show list of cards
        fun showTopics(cards: ArrayList<Topic>)

    }
    ...
```
> Contract is an interface which defines what methods given class must implements. You should declare contract for any class in your MVP.

### Content
More detailed view is represented by ContentFragment
<img src="https://github.com/tmaxxdd/MVPWithFirebase/blob/master/screenshot_content.png" align="left" width="200">
```kotlin
interface ContentContract {

    // View
    interface Fragment : IBaseFragment {

        fun showImage(source: String)

        fun showTitle(title: String)

        fun showDescription(text: String)

        fun showContent(contents: ArrayList<Content>)
    }
    ...
```
Here is more to do so interface must reflects view's options.

### Detail
The most specific content is show at the end in DetailsFragment.
<img src="https://github.com/tmaxxdd/MVPWithFirebase/blob/master/screenshot_detail.png" align="left" width="200">
```kotlin
interface DetailsContract {

    interface Fragment : IBaseFragment {

        fun showDetails(cards: ArrayList<Detail>)

    }
```
This window shows no more than again list of cards. Although views are pretty simple, you can easily add new elements. Just add components in xml and functions for them in contract. This is a superpower of MVP!

## Presenter
Presenter is a bridge between view and data source. It is also a manager which reacts on lifecycle.
```kotlin
class ContentPresenter(private val dataRepository: DataRepository):
        BasePresenter<ContentContract.Fragment>(), ContentContract.Presenter  {
    private val TAG = javaClass.simpleName

    override fun getContent(ref: String) {

        //Show loading
        view?.setProgressBar(true)

        dataRepository.getContent(ref, object : DataSource.GetContentCallback {

            override fun onSuccess(contents: ArrayList<Content>) {
                view?.let {
                    it.showContent(contents)
                    it.setProgressBar(false)
                }
            }

            override fun onFailure(throwable: Throwable) {
                view?.let{
                    it.setProgressBar(false)
                    it.showToast("App wasn't able to retrieve data")
                }
            }

            override fun onNetworkFailure() {
                view?.let{
                    it.setProgressBar(false)
                    it.showToast("App wasn't able to connect to the internet")
                }
            }

        })
    }
```
Presenter gets access to a view object from BasePresenter which manages view's state. Access is provided thanks to variable inheritance.

```kotlin
abstract class BasePresenter<ViewT>: IBasePresenter<ViewT> {

    protected var view: ViewT? = null

    override fun onViewActive(view: ViewT) {
        this.view = view
    }

    override fun onViewInactive() {
        view = null
    }

}
```
## Model
Generally model refers to a single immutable data class. In MVP we name as model everything that is related to a data in any case. Classical model approach: http://www.javapractices.com/topic/TopicAction.do?Id=187.
### DataRepository
The main source of 
