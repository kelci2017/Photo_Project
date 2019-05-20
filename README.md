# Photo_Project

**Ideas**

* This example was created with MVVM design
* Fetch photo list from server when Photos page opened
* Phto thumnail was downloaded in an asynctask
* Photo thumnail was saved in the memory cache once downloaded
* Always check whether the needed thumnail was in memory cache before downloading from internet, if so, populate the thumnail from memory cache
* When list items was clicked, fetching photo details from server
* The detail page was downloaded within an asynctask

* The view part is mainly the UI, such as the PhotoFragment and PhotolistFragment
* Fetching data from server is in the ViewModel
* Livedata was observed in the fragments and once changed, UI was updated
* The fetched data from server was processed in the model part, if the data source was changed, only the model part was involed
* I wrote a library restService for app http resquest, Volley was encapsulated and just need override some methods when request data from server

* The back end is in another repository App-server branch imageserver_test
* Some images are saved in the server and made public so that they can be requested in the app
* Some api was created to return photoslist and photo details to app
* The photo detail info was fake, just randomly assigned some data

**Demo**

![PhotosScreenshot](https://github.com/kelci2017/Photo_Project/blob/master/photolist.png)
![DetailScreenshot](https://github.com/kelci2017/Photo_Project/blob/master/photodetail.png)
![Demo](https://github.com/kelci2017/Photo_Project/blob/master/demo.gif)
