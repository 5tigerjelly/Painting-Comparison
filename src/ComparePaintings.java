/**
 * @author (your name goes here)
 *
 */
public class ComparePaintings {
	private boolean quadratic;
	
	public ComparePaintings(){
		quadratic = true;
	};
	
	// Load the image, construct the hash table, count the colors.
	ColorHash countColors(String filename, int bitsPerPixel) {
		ImageLoader image = new ImageLoader(filename);
		ColorHash colorHash;
		if(quadratic){
			colorHash = new ColorHash(3, bitsPerPixel, "Quadratic Probing", 0.5);
		}else{
			colorHash = new ColorHash(3, bitsPerPixel, "Linear Probing", 0.5);
		}
		for(int x = 0; x < image.getWidth(); x++){
			for(int y = 0; y < image.getHeight(); y++){
				colorHash.collisionCount += colorHash.increment(image.getColorKey(x, y, bitsPerPixel)).nCollisions;
			}
		}
		return colorHash;
	}
	
	private ColorHash countColorWithType(String filename, int bitsPerPixel, boolean quadraticCheck){
		quadratic = quadraticCheck;
		return countColors(filename, bitsPerPixel);
	}

//	Starting with two hash tables of color counts, compute a measure of similarity based on the cosine distance of two vectors.
	double compare(ColorHash painting1, ColorHash painting2) {
		
		FeatureVector vector = new FeatureVector(painting1.bitsPerPixel);
		FeatureVector vector2 = new FeatureVector(painting2.bitsPerPixel);
		vector.getTheCounts(painting1);
		vector2.getTheCounts(painting2);

		return vector.cosineSimilarity(vector2); 
	}

	//	A basic test for the compare method: S(x,x) should be 1.0, so you should compute the similarity of an image with itself and print out the answer. If it comes out to be 1.0, that is a good sign for your implementation so far.
	void basicTest(String filename) {
		
		ImageLoader image = new ImageLoader(filename);
		ColorHash colorHash = new ColorHash(3, 24, "Linear Probing", 0.5);//FIX THIS
		
		double check = compare(colorHash, colorHash);
		System.out.println("check value was " + check); 
		//TODO FIX THIS LATER RETURNING 1.00000000000000002
	}
	
	private int loadColorHash(String filename, int bitsPerPixel, String probing, double loadFactor){
		ImageLoader image = new ImageLoader(filename);
		ColorHash colorHash = new ColorHash(3, bitsPerPixel, probing, loadFactor);
		int collCount = 0;
		for(int x = 0; x < image.getWidth(); x++){
			for(int y = 0; y < image.getHeight(); y++){
				collCount += colorHash.increment(image.getColorKey(x, y, bitsPerPixel)).nCollisions;
			}
		}
		return collCount;
	}

	//		Using the three given painting images and a variety of bits-per-pixel values, compute and print out a table of collision counts in the following format:
	void CollisionTests() {		
		final Object[][] table = new String[9][];
		table[0] = new String[] { "Bits Per Pixel", "C(Mona,linear)", "C(Mona,quadratic)", "C(Starry,linear)", "C(Starry,quadratic)", "C(Christina,linear)", "C(Christina,quadratic)"};
		for(int i = 24; i > 0; i -= 3){
			table[9-i/3] = new String[] { ""+i, 
					""+countColorWithType("MonaLisa.jpg", i, false).collisionCount,
					""+countColorWithType("MonaLisa.jpg", i, true).collisionCount,
					""+countColorWithType("StarryNight.jpg", i, false).collisionCount,
					""+countColorWithType("StarryNight.jpg", i, true).collisionCount,
					""+countColorWithType("ChristinasWorld.jpg", i, false).collisionCount,
					""+countColorWithType("ChristinasWorld.jpg", i, true).collisionCount};
		}
		
		for (final Object[] row : table) {
		    System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n", row);
		}
	}
	
	void fullSimilarityTests(){
		System.out.println();
		quadratic = true;
		final Object[][] table = new String[9][];
		table[0] = new String[] { "Bits Per Pixel", "S(Mona,Starry)", "S(Mona,Christina)", "S(Starry,Christina)"};
		for(int i = 24; i > 0; i -= 3){
			table[9-i/3] = new String[] { ""+i, 
					""+fv(i, "MonaLisa.jpg", "StarryNight.jpg"),
					""+fv(i, "MonaLisa.jpg", "ChristinasWorld.jpg"),
					""+fv(i, "ChristinasWorld.jpg", "StarryNight.jpg")};
			System.out.println("check");
		}
		
		for (final Object[] row : table) {
		    System.out.format("%-15s%-15s%-15s%-15s\n", row);
		}
	}
	
	private double fv(int bpp, String fileName, String fileName2){
		return compare(countColors(fileName, bpp), countColors(fileName2, bpp));
	}
	
	private void checkBlack(){
		try{
			ColorHash ch = countColors("MonaLisa.jpg", 6);
			ColorKey black = new ColorKey(0, 6);
			System.out.println("There are " + ch.colorHashGet(black).value + " black pixels.");
		}catch(Exception e){
			
		}
		
	}
		
// This simply checks that the images can be loaded, so you don't have 
// an issue with missing files or bad paths.
	void imageLoadingTest() {
		ImageLoader mona = new ImageLoader("MonaLisa.jpg");
		ImageLoader starry = new ImageLoader("StarryNight.jpg");
		ImageLoader christina = new ImageLoader("ChristinasWorld.jpg");
		System.out.println("It looks like we have successfully loaded all three test images.");

	}
	/**
	 * This is a basic testing function, and can be changed.
	 */
	public static void main(String[] args) {
		ComparePaintings cp = new ComparePaintings();
//		cp.imageLoadingTest();
//		cp.basicTest("MonaLisa.jpg");
		cp.CollisionTests();
		cp.fullSimilarityTests();
		cp.checkBlack();
	}

}
