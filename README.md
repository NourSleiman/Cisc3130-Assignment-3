# Cisc3130-Assignment-3

One csv file is stored in an array so its data can be read into a 2D array. 
The title is in the first column while the year is in the second column.

After all information is read into the array, the elements are then inputted into the binary search tree which sorts them alphabetically.
The titles get continuously compared against the root. If the title name comes before the root's, it is sorted into the left side.
If the title comes after the root's, it is sorted into the right side. 

The movies can be searched through a range. The movies that lie between that range are put into an ArrayList.
The movies in that ArrayList are then printed out into a file called "movieSubSets.txt" in the output folder of the data folder.
