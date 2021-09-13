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
          implementation 'com.github.vsnappy1:RecyclerViewDummyData:1.1.2
  }
  
  
Step 3. 
  Create an instance of RecyclerViewDummyData and call loadDummyData method and pass in the Context and RecyclerView instance
  
  e.g.
  
        // Create an instance of RecyclerViewDummyData with the Builder class
        recyclerViewDummyData = RecyclerViewDummyData.Builder()
            .setDummyViewResourceId(R.layout.item_product_dummy)	// set the dummy layout resource id
            .setAnimationType(AnimationType.SHIMMER)		// Optional you can select between different animation types, default value is AnimationType.FADE_IN_FADE_OUT
            .build()						// This builds the RecyclerViewDummyData instance with given configuration

        // This populates the recycler view with dummy data
        recyclerViewDummyData?.loadDummyData(this, recyclerView)
        ...
        ...
        // Once data is fetched from server set adapter property of recycler view to orignal adapter
        recyclerView.adapter = productAdapter

Note.

RecyclerViewDummyData is highly customizable you can customize it in many ways, checkout below few examples
  
  e.g.
  
        // This create an instance of RecyclerViewDummyData with given dummy layout resource id, and its the simpletest one with all default configuration
        recyclerViewDummyData = RecyclerViewDummyData.Builder()
            .setDummyViewResourceId(R.layout.item_product_dummy)
            .build()


        // This create an instance of RecyclerViewDummyData with given dummy layout resource id and animation type as SHIMMER
        recyclerViewDummyData = RecyclerViewDummyData.Builder()
            .setDummyViewResourceId(R.layout.item_product_dummy)
            .setAnimationType(AnimationType.SHIMMER)
            .build()
	    
	    
        // This create an instance of RecyclerViewDummyData with given dummy layout resource id and the custom animation
        recyclerViewDummyData = RecyclerViewDummyData.Builder()
            .setDummyViewResourceId(R.layout.item_product_dummy)
            .setCustomAnimationResourceId(R.anim.translate_fade_anim)
            .build()
	    
	    
        // This create an instance of RecyclerViewDummyData with given dummy layout resource id, custom animation, itemCount as 4 and animation speed as 5
        recyclerViewDummyData = RecyclerViewDummyData.Builder()
            .setDummyViewResourceId(R.layout.item_product_dummy)
            .setAnimationType(AnimationType.SHIMMER)
            .setItemCount(4)		
            .setAnimationSpeed(5f)
            .build()
	    
	   
Troubleshot. 

If you are unable to see the dummy data or the animation effect, check the logs with a tag of 'RecyclerViewDummyData' 
hopefully you will find the cause and solve the pronlem
  
  e.g.
  
        D/RecyclerViewDummyData: ERROR: No custom animation resource id provided
	    
	
