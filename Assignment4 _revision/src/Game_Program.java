/**
 * CS1A, Assignment 4, "Guessing Game" <br>
 * Quarter: Fall 2020 <br>
 * Instructor: Baba Kofi Weusijana <br>
 *  This program creates a game where the user will be given at most 7 attempts to 
 * guess a secret number that the program has chosen. To begin, the user is introduced 
 * to the game and shown a set of rules. Throughout the game, the user will be given 
 * feedback from the program. At the conclusion of the game, the user can choose to play again.
 * <br>
 *  The guessingGame class contains the maximum and minimum parameters of the guesses,
 * keeps track of the number of guesses made, and the number of guesses left. The 
 * secret number generator is also declared in this class, and the secret number is 
 * declared here too. This class contains various helper methods that help in its working 
 * properly.  
 * <br>
 * <br>
 *  The most efficient way to win the Guessing Game is to use the Binary Search method.
 * This method works by repeatedly guessing the middle number in a range,
 * effectively halving the list that could contain the secret number. For example,
 * given our range of -64 to 64, the user's first guess should be 0. Assuming the secret 
 * number is not 0, the program will determine if this guess is greater or less than the 
 * secret number. This means the set of possible secret numbers has been halved to be
 * either -64 to -1 or 1 to 64. Repeatedly using this strategy will ensure the user can 
 * always win the game within 7 guesses.
 * 
 * @author Aryan Gorwade
 * @author Mary Boyajian
 */
public class Game_Program extends Object
{
   public static void main(String[] args)
   {
      GuessingGame gg = new GuessingGame(); // A new guessingGame object is constructed

      boolean playing = true;
      while (playing)
      {
         playing = gg.playGuessingGame(); // playGuessingGame will return true if the user wants to play
      }                                   // again and false if the user doesn't want to play again. This 
   }                                      // ensures that the loop will be exited only if the user doesn't 
}                                         // want to play again.

