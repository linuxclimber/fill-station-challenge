
# Fill Station Program
Program Overview:
- Creates world map on a virtual grid of -10 to +10 in both the X and Y axis
- Randomly generates list of medications with unique names
- Generates a random number of central fill stations each with a random inventory of medications from the previously generated list of medications and assigns each medication in its inventory a random non-zero price unique to that medication at that particular fill station
- Assigns each fill station to a random and unique location on the world map
- Program takes input from user in the form of a pair of (x,y) coordinates
- Calculates the closest fill centers based on Manhattan distances and returns the 3 nearest fill stations to the user provided coordinates, the cheapest medication at each of those fill stations, the price of that medication, and the distance the fill station is from the coordinates provided by the user


# Run Instructions
- Clone the repository from github
- Open a terminal/shell and navigate to the directory where you cloned the repository
- Run './gradlew build' to build the program and after that completes run './gradlew --console plain -q run' to execute the program
    - If you are using a Windows system, you may need to replace gradlew with gradlew.bat to execute the program


# Assumptions
- Although some basic validation was done on user input, I am assuming the user is not malicious/will not be providing malicious input in their input and there is therefore not a need to sanitize it/treat it as potentially hostile
- Central Fill Facility identifiers are assigned sequentially starting at 001 and their uniqueness is maintained when the data is generated (if this were being done with a database I am assuming there would be restrictions on the uniqueness of the identifier there rather than in the code)
- Prices must be both non-zero and positive in value
- If two or more fill stations are the same distance from the given point and within the closest 3, it does not matter which of those stations are returned to the user
- If more than three stations are tied for the closest distance to the given point, only 3 of those stations will be returned to the user.
- Each fill station has a random inventory of a random number of medications and may therefore share some or no medications with other fill stations depending on the random seeding


# Changes
- To support multiple fill stations at a single location, I would most likely add a Location object that would take the existing place of the fill stations on the map and would contain a reference to the fill stations that could be found there.  There shouldn't be any major adjustments needed to accomodate this outside of the extra layer around getting the fill stations for a particular location
- To support a larger world, the main focuses would be on performance/optimization.  Particularly with the code that determines the 3 nearest stations.  Currently the number of stations as well as the world size are all set up as configurable values so they can be easily adjusted.  However, as the world size increases to large enough sizes, it would likely make more sense to try and implement buckets or zones of some sort to help minimize the area that needs to be searched for the nearest stations.  The exact approach would depend largely on whether data was still being randomly seeded or was fixed as well as the density of that data in either case.  For a more sparsely filled world map, a similar approach to what is currently being done might be more optimal while a densely packed world would definitely benefit from taking a different approach most likely involving zoning the map into various sections and starting the search based on the closest sections first