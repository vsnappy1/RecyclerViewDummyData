# RecyclerViewDummyData
A library to show items loading effect in RecyclerView

Step 1. Add the JitPack repository to your build file
  
  
    allprojects {
		  repositories {
			  ...
			  maven { url 'https://jitpack.io' }
        }
      }
  
  
Step 2. Add the dependency
	
    dependencies {
          ...
          implementation 'com.github.vsnappy1:RecyclerViewDummyData:1.0.5
  }
  
  
Step 3. 
  Create instance of RecyclerViewDummyData and call setDummyData method and pass in the recyclerView instance and the dummy layout id
  
  e.g.
  
     //Initialize the RecyclerViewDummyData object and pass in the context
        recyclerViewDummyData = RecyclerViewDummyData(this)
        
     //sets the dummy data into the recycler view
     //First parameter to setDummyData method is the instance of RecyclerView
     //Second parameter to setDummyData method is the id of the dummy layout
     //Third(optional, default value is 6) parameter to setDummyData method is the number of items to show
     //Fourth(optional) parameter to setDummyData method is the Id of repeating animation
     
        recyclerViewDummyData.setDummyData(recyclerView, R.layout.item_product_dummy)
        
     //Once data is fetched from server set adapter property of recycler view to orignal adapter
        recyclerView.adapter = productAdapter
