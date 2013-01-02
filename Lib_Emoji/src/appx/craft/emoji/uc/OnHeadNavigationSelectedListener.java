package appx.craft.emoji.uc;

import appx.craft.emoji.bean.ImageBean;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving onHeadNavigationSelected events.
 * The class that is interested in processing a onHeadNavigationSelected
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addOnHeadNavigationSelectedListener<code> method. When
 * the onHeadNavigationSelected event occurs, that object's appropriate
 * method is invoked.
 *
 * @see OnHeadNavigationSelectedEvent
 */
public interface OnHeadNavigationSelectedListener {
	  
  	/**
  	 * On button selected.
  	 *
  	 * @param tag the tag
  	 */
  	public void onButtonSelected(ImageBean objBean);
}