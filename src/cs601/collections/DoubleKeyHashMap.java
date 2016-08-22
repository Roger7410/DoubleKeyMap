package cs601.collections;

import java.util.ArrayList;
import java.util.List;

public class DoubleKeyHashMap<K1,K2,V> implements DoubleKeyMap<K1,K2,V> {

	public int size;
	public List<Entry<K1, K2, V>> e_buckets [];

	/**Constructor with no arguments.*/
	public DoubleKeyHashMap(){
		e_buckets = new ArrayList[1000];  //default hashtable size 100
	}

	/**Constructor with an argument specifying the (int) number of buckets*/
	public DoubleKeyHashMap(int buckets_num){
		e_buckets = new ArrayList[buckets_num];
		// you might as well initialize the buckets here so you don't have to worry about it later.
	}

	/** Add (key1,key2,value) to dictionary, overwriting previous
     *  value if any.  key1, key2, and value must all be non-null.
     *  If any is null, throw IllegalArgumentException.
     *  @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     */
	//@Override
	public V put(K1 key1, K2 key2, V value) {
		if(key1 == null || key2 == null || value == null){
			throw new IllegalArgumentException();
		}
		int hash = Math.abs(key1.hashCode()*37 + key2.hashCode());// hash function should probably be in its own function
		int index = hash % e_buckets.length ;

		if( e_buckets[index] == null ){
			e_buckets[index] = new ArrayList<Entry<K1,K2,V>>();
			e_buckets[index].add(new Entry<K1, K2, V>(hash, key1, key2, value));
			size++;
		}
		else{
			for(int i = 0; i < e_buckets[index].size(); i++){
				Entry< K1, K2, V> e = e_buckets[index].get(i);
				// you should define equals in Entry and then use the standard array list. No reason to do it yourself
				if(e.hash == hash && e.key1.equals(key1) && e.key2.equals(key2) && e.value != value){
					V oldValue = e.value;
					e.value = value;
					return oldValue;
				}
			}
			e_buckets[index].add(new Entry<K1, K2, V>(hash, key1, key2, value));
			size++;
			//return null;
		}
		return null;
	}

	/** Return the value associated with (key1, key2). Return null if
     *  no value exists for those keys.  key1, key2 must be non-null.
     *  If any is null, throw IllegalArgumentException.  The value
     *  should be just the value added by the user via put(), and          ?
     *  should not contain any of your internal bookkeeping
     *  data/records.
     */
	@Override
	public V get(K1 key1, K2 key2) {
		if(key1 == null || key2 == null){
			throw new IllegalArgumentException();
		}
		int hash = Math.abs(key1.hashCode()*37 + key2.hashCode());
		int index = hash % e_buckets.length;
		if( e_buckets[index] != null){
		for(int i = 0; i < e_buckets[index].size(); i++){
			Entry< K1, K2, V> e = e_buckets[index].get(i);
			if(e.hash == hash && e.key1.equals(key1) && e.key2.equals(key2)){
				return e.value;
			}
		}
		}
		return null;
	}

	/** Remove a value if present. Return previous value if any.
     *  Do nothing if not present.
     */
	@Override
	public V remove(K1 key1, K2 key2) {
		int mark = 0;
		if( get(key1, key2) == null ){
			return null;
		}
		else{
			int hash = Math.abs(key1.hashCode()*37 + key2.hashCode());
			int index = hash % e_buckets.length;
			V value = null;
			for(int i = 0; i < e_buckets[index].size(); i++){
				Entry< K1, K2, V> e = e_buckets[index].get(i);
				if(e.hash == hash && e.key1.equals(key1) && e.key2.equals(key2) ){
					value = e.value;
					mark = i;
					e_buckets[index].remove(mark);
					// very inefficient because remove will have to walk the list again
					// also, it is incorrect as it will be checking identity of the objects not keep equality.
					// you do not have a definition of equality in Entry
					size--;
				}
			}
			return value;
		}
	}

	/** Return true if there is a value associated with the 2 keys
     *  else return false.
     *  key1, key2 must be non-null. If either is null, return false.
     */
	@Override
	public boolean containsKey(K1 key1, K2 key2) {
		return get(key1, key2) != null ;
	}

	/** Return list of a values in the map/dictionary.  Return an
     *  empty list if there are no values.  The values should be just
     *  the values added by the user via put(), and should not contain
     *  any of your internal bookkeeping data/records.
     */
	@Override
	public List<V> values() {
		List<V> v_list = new ArrayList<V>();
		for(int i = 0; i < e_buckets.length; i++){
			if(e_buckets[i] != null){
			for(int j = 0; j < e_buckets[i].size() ; j++){
				if(e_buckets[i].get(j).value != null){ // I'm not sure why you are checking for null value here
					v_list.add(e_buckets[i].get(j).value);
				}
			} // need to indent this
			}
		}
		return v_list;
	}

	/** Return how many elements there are in the dictionary. */
	@Override
	public int size() {
		return size;
	}

	/** Reset the dictionary so there are no elements. */
	@Override
	public void clear() {
		for(int i = 0; i < e_buckets.length; i++){
			e_buckets[i] = null;
		}
		size = 0;
	}

	static class Entry<K1, K2, V> {
		K1 key1;
		K2 key2;
		V value;
		int hash;

		Entry(int h, K1 k1, K2 k2, V v){
			hash = h;
			key1 = k1;
			key2 = k2;
			value = v;
		}

		/*public final K1 getKey1(){
			return key1;
		}

		public final K2 getKey2(){
			return key2;
		}

		public final V getValue(){
			return value;
		}*/
	}

}






