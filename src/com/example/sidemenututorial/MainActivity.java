package com.example.sidemenututorial;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockFragmentActivity {

	// Fields -----------------------------------------------------------------
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;
	private MenuListAdapter menuAdapter;
	private int[] icons;
	private Fragment fragment1;
	private Fragment fragment2;
	private Fragment fragment3;
	private CharSequence drawerTitle;
	private CharSequence title;
	private final String[] titles = new String[]{
			"Title Fragment #1",
			"Title Fragment #2",
			"Title Fragment #3"
	};
	private final String[] subtitles = new String[]{
			"Subtitle Fragment #1",
			"Subtitle Fragment #2",
			"Subtitle Fragment #3"
	};
	
	// Lifecycle Callbacks ----------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// Base implemenation
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Instantiate the fragments
		fragment1 = new Fragment1();
		fragment2 = new Fragment2();
		fragment3 = new Fragment3();
		
		// Get the title from this activity
		title = drawerTitle = getTitle();
		
		// Get the icons from the drawables folder
		icons = new int[]{
				R.drawable.action_about,
				R.drawable.action_settings,
				R.drawable.collections_cloud
		};
		
		// Get the drawer layout from the XML file and the ListView inside it
		drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		drawerList = (ListView)findViewById(R.id.listview_drawer);
			
		// Set a custom shadow over that overlays the main content
		// when the drawer opens
		drawerLayout.setDrawerShadow(
				R.drawable.drawer_shadow, GravityCompat.START);
		
		// Pass the string arrays to the MenuListAdapter, set the drawer
		// list adapter to it and set up its click listener
		menuAdapter = new MenuListAdapter(
				MainActivity.this, titles, subtitles, icons);
		drawerList.setAdapter(menuAdapter);
		drawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		// Enable the action bar to have up navigation
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Allow the the action bar to toggle the drawer
		drawerToggle = new ActionBarDrawerToggle(
				this,
				drawerLayout,
				R.drawable.ic_drawer,
				R.string.drawer_open,
				R.string.drawer_close){
			
			public void onDrawerClosed(View view){
				super.onDrawerClosed(view);
			}
			public void onDrawerOpened(View view){
				getSupportActionBar().setTitle(drawerTitle);
				super.onDrawerOpened(view);
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);
		
		// If this is the first time opening this activity,
		// start with loading fragment #1
		if (savedInstanceState == null){
			selectItem(0);
		}		
		
	}

	// Methods ----------------------------------------------------------------
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		// If the user has pressed the action bar icon
		if (item.getItemId() == android.R.id.home){
			
			// If the drawer is open, close it; vice versa
			if (drawerLayout.isDrawerOpen(drawerList)){
				drawerLayout.closeDrawer(drawerList);
			} else {
				drawerLayout.openDrawer(drawerList);
			}
		}
		
		// Finish by letting the super class do the rest
		return super.onOptionsItemSelected(item);
		
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState){
		
		// Call the super implementation and synchronize the drawer
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
		
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		
		// Call the super implemenation on this activity
		// and the drawer toggle object
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
		
	}
	private void selectItem(int position){
		
		// Create a new fragment transaction and start it
		FragmentTransaction fragTran = getSupportFragmentManager()
									   .beginTransaction();
		
		// Locate the position selected replace the content view
		// with the fragment of the number selected
		switch (position){
			case 0:{
				fragTran.replace(R.id.content_frame, fragment1);
				break;
			}
			case 1:{
				fragTran.replace(R.id.content_frame, fragment2);
				break;
			}
			case 2:{
				fragTran.replace(R.id.content_frame, fragment3);
				break;
			}
		}
		
		// Commit the transaction and close the drawer
		fragTran.commit();
		drawerList.setItemChecked(position, true);
		drawerLayout.closeDrawer(drawerList);
		
	}
	public void setTitle(CharSequence title){
		
		// Save the passed in title and set the action bar title
		this.title = title;
		getSupportActionBar().setTitle(title);
		
	}

	// Classes ----------------------------------------------------------------
	private class DrawerItemClickListener 
	implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(
				AdapterView<?> parent, 
				View view, 
				int position,
				long id) {
			
			// When clicked, select open the appropriate fragment
			selectItem(position);
		
		}
		
	}
	
}
