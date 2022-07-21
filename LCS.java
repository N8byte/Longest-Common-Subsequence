/* Nathan Waskiewicz
 * Dr. Andrew Steinberg
 * COP3503 Summer 2022
 * Programming Assignment 3
 */

public class LCS {
    private String x;
    private String y;
    private String lcs;
    private int[][] c = new int[1000][1000]; //the table which holds the length of the lcs
    private DIR[][] b = new DIR[1000][1000]; //the table which holds the path of the lcs

    enum DIR //used for the b table to find the directions to traverse the path backwards
    {
        h, d, v //horizontal, diagonal, vertical
    }


    //getter for longest common subsequence
    public LCS(String x, String y)
    {
        this.x = x;
        this.y = y;
    }


    //recursively traverses the table with directions to know which characters to print
    private String recurPrint(String result, int i, int j)
    {
        if (i == 0 || j == 0) //base case, we've reached the end!
            return result;

        switch (b[i][j]) //do things based on direction
        {
            case d: //if diagonal add character in reverse order
                result = result.concat(recurPrint(result, i-1, j-1));
                result = result + x.charAt(i-1);
                break;
            case v: //if vertical traverse vertically
                result = result.concat(recurPrint(result, i-1, j));
                break;
            case h: //if horizontal traverse horizontally
                result = result.concat(recurPrint(result, i, j-1));
                break;
        }

        return result;
    }

    //uses helper function to print the longest common subsequence
    public String getLCS()
    {
        String result = recurPrint("", x.length(), y.length());
        return result;
    }

    //dynamic programming solution to lcs!
    //uses the table to keep track of lcs' and direction taken
    public void lcsDynamicSol()
    {
        //set left column and bottom row to 0's
        for (int i = 0; i < x.length(); i++)
            c[i][0] = 0;
        for (int j = 0; j < y.length(); j++)
            c[0][j] = 0;

        //loop through row by row setting directions and largest lcs
        for (int i = 1; i < x.length() + 1; i++)
        {
            for (int j = 1; j < y.length() + 1; j++)
            {
                //if xi and yj are the same pull diagonally and add 1
                if (x.charAt(i-1) == y.charAt(j-1))
                {
                    c[i][j] = c[i-1][j-1] + 1;
                    b[i][j] = DIR.d;
                }
                //else pull the max between the horizontal and vertical,
                //with vertical breaking the tie
                else if (c[i-1][j] >= c[i][j-1])
                {
                    c[i][j] = c[i-1][j];
                    b[i][j] = DIR.v;
                }
                else
                {
                    c[i][j] = c[i][j-1];
                    b[i][j] = DIR.h;
                }
            }
        }
    }
}
