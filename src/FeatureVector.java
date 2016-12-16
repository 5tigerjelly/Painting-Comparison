/**
 * @author (your name goes here)
 *
 */
public class FeatureVector {

	/**
	 * FeatureVector is a class for storing the results of
	 * counting the occurrences of colors.
	 * <p>
	 * Unlike the hash table, where the information can be
	 * almost anyplace with the array(s) (due to hashing), in the FeatureVector,
	 * the colors are in their numerical order and each count
	 * is in the array position for its color.
	 * <p>
	 * Besides storing the information, the class provides methods
	 * for getting the information (getTheCounts) and for computing
	 * the similarity between two vectors (cosineSimilarity).
	 */
	long[] colorCounts;
	int bitsPerPixel;
	int keySpaceSize;

	/**
	 * Constructor of a FeatureVector.
	 * 
	 * This creates a FeatureVector instance with an array of the
	 * proper size to hold a count for every possible element in the key space.
	 * 
	 * @param bpp	Bits per pixel. This controls the size of the vector.
	 * 				The keySpace Size is 2^k where k is bpp.
	 * 
	 */
	public FeatureVector(int bpp) {
		keySpaceSize = 1 << bpp;
		colorCounts = new long[keySpaceSize];
		bitsPerPixel = bpp;
	}

	//TODO remove throws later
	public void getTheCounts(ColorHash ch) {
		if(ch.getTableSize() != keySpaceSize){
			System.out.println("keySpaceSize is "+keySpaceSize +" tableSize is " + ch.getTableSize());
		}
//		int bound = Math.min(ch.getTableSize(), keySpaceSize);
//		ch.
		for(int i = 0; i < keySpaceSize; i++){
			System.out.println(i);
			try {
				ColorKey key = new ColorKey(i, bitsPerPixel); 
				colorCounts[i] =  ch.getCount(key);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//ColorKey key = new ColorKey(i, bitsPerPixel);
		}
		
	}
	
	public double cosineSimilarity(FeatureVector other) {	
		double result = dotProduct(other.colorCounts)/(findMagnitude(colorCounts) * findMagnitude(other.colorCounts));
		return result;

	}
	
	private double dotProduct(long[] a){
		double result = 0.0;
		for(int i = 0; i < a.length; i++){
			result += (double)a[i] * (double)colorCounts[i];
		}
		return result;
	}
	
	private double findMagnitude(long[] a) {
		double result = 0.0;
		for(int i = 0; i < a.length; i++){
			result += (double)a[i] * (double)a[i];
		}
		result = Math.sqrt(result);
		return result;
	}

	/**
	 * Optional main method for your own tests of these methods.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
