import java.util.Random;
import java.util.Scanner;

/**
 * This program creates a game where the user will be given at most 7 attempts
 * to guess a secret number that the program has chosen. To begin, the user is
 * introduced to the game and shown a set of rules. Throughout the game, the
 * user will be given feedback from the program. At the conclusion of the game,
 * the user can choose to play again. <br>
 * The guessingGame class contains the maximum and minimum parameters of the
 * guesses, keeps track of the number of guesses made, and the number of guesses
 * left. The secret number generator is also declared in this class, and the
 * secret number is declared here too. This class contains various helper
 * methods that help in its working properly. <br>
 * <br>
 * The most efficient way to win the Guessing Game is to use the Binary Search
 * method. This method works by repeatedly guessing the middle number in a
 * range, effectively halving the list that could contain the secret number. For
 * example, given our range of -64 to 64, the user's first guess should be 0.
 * Assuming the secret number is not 0, the program will determine if this guess
 * is greater or less than the secret number. This means the set of possible
 * secret numbers has been halved to be either -64 to -1 or 1 to 64. Repeatedly
 * using this strategy will ensure the user can always win the game within 7
 * guesses.
 * 
 * @author Aryan Gorwade
 * @author <partner prefers to remain anonymous>
 */
public class GuessingGame extends Object
{
    // Instance variables, including constants, are listed here.
    private static final int MAX_POSSIBLE_GUESS = 64;
    private static final int MIN_POSSIBLE_GUESS = -64;
    private static final int MAX_GUESSES = 7;
    private int[] guesses = new int[MAX_GUESSES]; // Array to hold the user's 7 guesses
    private int secretNumber = getRandomNumber();
    private static Random randomNumberGenerator = new Random();
    private int guessesMade = 0;
    final static private int INVALID_NUMBER = MAX_POSSIBLE_GUESS + 1; // Out of range default value that populates the
                                                                      // array

    /**
     * This method returns a number between -64 and 64. The generated number is
     * inclusive of -64 and 64, meaning its value could be -64 or 64. There are a
     * total of 129 separate numbers that could be generated.
     * 
     * @return a number between -64 and 64 inclusive of -64 and 64
     */
    public int getRandomNumber()
    {
        int max = MAX_POSSIBLE_GUESS - MIN_POSSIBLE_GUESS;
        int zeroToMax = randomNumberGenerator.nextInt(max + 1);
        return zeroToMax + MIN_POSSIBLE_GUESS;
    }

    /**
     * This method prints a welcome message and a set of 3 rules to the user.
     */
    public void welcomeMessage()
    {
        System.out.println("Welcome to the guessing game!");
        System.out.println("Rules: ");
        System.out.println("1) You have " + MAX_GUESSES + " guesses");
        System.out.println("2) If you guess the wrong number, a hint will be displayed");
        System.out.println("3) Your guess has to be between " + MIN_POSSIBLE_GUESS + " and " + MAX_POSSIBLE_GUESS);
        System.out.println();

    }

    // You may want to create a method (e.g., isGuessNum) that will check to see
    // that the number entered is a whole number,
    // assign it to a variable like userGuess, and then handle all
    // the various options regarding that number: has it been guessed before?; is it
    // the secret number?; if it isn't the secret number store it in the proper
    // location for cross checking; display messages regarding the number of guesses
    // made,
    // incorrect guesses, guesses remaining, secret number if all guesses used up,
    // error handling, etc.
    /**
     * This method will check if the user has previously guessed the current guess
     * and will also check if the guess is in range (from -64 to 64 inclusive). If
     * either of these conditions is false, it will keep asking the user for input
     * until the user enters input that satisfies these 2 conditions. If the user
     * guesses the correct number, the congratulation message will be printed. With
     * each guess, the program will let the user know how many guesses they've made
     * and how many guesses are left.
     */

    public void isGuessNum()
    {
        boolean done = false;

        while (!done)
        {
            int userGuess = getInput(MIN_POSSIBLE_GUESS, MAX_POSSIBLE_GUESS);

            while (previouslyGuessed(userGuess))
            {
                System.out.println("You have already guessed this number. Please" + " guess another number");
                userGuess = getInput(MIN_POSSIBLE_GUESS, MAX_POSSIBLE_GUESS);

            }

            if (!previouslyGuessed(userGuess))
            {
                if (userGuess == secretNumber)
                {
                    System.out.println("Congratulations! You guessed the secret number: " + secretNumber);
                    done = true;
                    storeGuess(userGuess);
                    playGame();
                    reset();

                } else
                {
                    ++guessesMade;
                    storeGuess(userGuess);
                    printPreviousGuesses(guesses);
                    giveUserHint(userGuess);
                    System.out.println("You have made " + (MAX_GUESSES - guessesLeft()) + " guess(es).");
                    System.out.println("You have " + (guessesLeft()) + " guess(es) left.");
                    done = true;

                    if (guessesLeft() == 0)
                    {
                        System.out.println("You failed to guess the secret number.");
                        System.out.println("The secret number was: " + secretNumber);
                        done = true;
                    }

                }
            } else if (!guessInRange(userGuess, MIN_POSSIBLE_GUESS, MAX_POSSIBLE_GUESS))
            {
                System.out.print(userGuess);
                System.out.println(" is NOT an integer in the valid range!");
            }

        }
    }

    /**
     * Prints previous guess(es) user has made in the game.
     * 
     * @param guesses the array of user guesses
     */
    public void printPreviousGuesses(int[] guesses)
    {
        System.out.print("Your previous guess(es): ");
        for (int i = 0; i < guessesMade; ++i)
        {
            System.out.print(guesses[i]);
            System.out.print(", ");
        }
        System.out.println(" ");
    }

    /**
     * Store the user's guess in the next element of the array
     * 
     * @param entry the user's guess that needs to be stored
     */
    public void storeGuess(int entry)
    {
        int i = guessesMade - 1;
        guesses[i] = entry;
    }

    /**
     * Returns the number of guesses the user has left in the game (out of a maximum
     * of 7)
     * 
     * @return number of guesses left
     */
    public int guessesLeft()
    {
        int i = guessesMade;
        return MAX_GUESSES - i;

    }

    /**
     * This method gets an integer guess from the user, checks if the guess is in
     * bounds (-64 and 64, inclusive of -64 and 64). If the guess is out of bounds,
     * an error message is printed that tells the user to input a valid integer in
     * the range, and the user is prompted to enter another guess. The method keeps
     * asking the user for an integer that is in bounds until the user enters an
     * integer that is in bounds. If the entered input is not an integer, the
     * program will print an error message telling the user to input a valid
     * integer. It will keep telling the user to input a valid integer until the
     * user does so. This method will only return a valid integer guess that is in
     * the range.
     * 
     * @return input; the valid, in-range, and previously unguessed integer guess
     *         from the user
     * @param min, the minimum value of the range;
     * @param max, the maximum value of the range
     */
    public int getInput(int min, int max)
    {
        int input = 0;
        Scanner in = new Scanner(System.in);
        boolean done = false;
        System.out.println("Please enter a valid integer between " + min + " and " + max + " (" + min + " and " + max
                + " are valid guesses)");

        while (!done)
        {
            if (in.hasNextInt())
            {
                input = in.nextInt();
                in.nextLine();

                if (guessInRange(input, min, max))
                {
                    System.out.println("You guessed: " + input);
                    done = true;

                } else
                {
                    System.out.println(input + " is not a valid integer. Please enter a valid integer between " + min
                            + " and " + max + " (" + min + " and " + max + " are valid guesses)");
                }
            } else
            {
                String input2 = in.nextLine();
                System.out.println(input2 + " is not a valid integer. Please enter a valid integer: ");
            }
        }

        return input;

    }

    /**
     * This method checks if the user's guess is in intended range (-64 to 64
     * inclusive)
     * 
     * @param input the user's guess
     * @param min   the minimum value a user can guess
     * @param max   the maximum value a user can guess
     * @return true if guess is in range
     */
    public boolean guessInRange(int input, int min, int max)
    {
        boolean inRange = false;

        if (min <= input && input <= max)
        {
            inRange = true;
        }

        return inRange;
    }

    /**
     * Checks if the user has previously guessed the number (within the same game)
     * 
     * @param input user's guess
     * @return true if user has previously guessed the number
     */
    public boolean previouslyGuessed(int input)
    {
        boolean isMatched = false;
        int currentGuess;

        for (int i = 0; i < guessesMade; ++i)
        {
            currentGuess = guesses[i];

            if (currentGuess == input)
            {
                isMatched = true;
                return isMatched;
            } else
            {
                isMatched = false;
            }
        }
        return isMatched;
    }

    /**
     * Checks if the user's guess matches the program's secret number
     * 
     * @param input user's guess
     * @return true if the user has successfully guessed the secret number
     */
    public boolean matchesSecret(int input)
    {
        boolean correct = false;

        if (secretNumber == input)
        {
            correct = true;
        } else
        {
            correct = false;
        }
        return correct;
    }

    /**
     * Prints out a hint about the secret number by indicating if the secret number
     * is greater than or less than the user's guess
     * 
     * @param input user's guess
     */
    public void giveUserHint(int input)
    {
        if (input < secretNumber)
        {
            System.out.println("Hint: The secret number is GREATER than " + input);
        } else if (input > secretNumber)
        {
            System.out.println("Hint: The secret number is LESS than " + input);
        }

    }

    /**
     * This method will ask the user if they would like to play again. According to
     * their answer, it will either exit the game (after resetting the game) or it
     * will lead the user to start a new game. In case of invalid input, the user is
     * again prompted to provide valid input.
     * 
     * @return true if the user wants to play again
     */

    public boolean playGame()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Do you want to play again?");
        System.out.print("Enter '1' for yes or");
        System.out.println(" '0' for no.");
        boolean wantsToPlayAgain = false;
        boolean done = false;

        while (!done)
        {
            if (in.hasNextInt())
            {
                int input = in.nextInt();

                if (input == 1)
                {
                    System.out.println("You have chosen to play again!");
                    wantsToPlayAgain = true;
                    done = true;

                }

                else if (input == 0)
                {
                    System.out.println("You have chosen not to play again. Please come back soon!");
                    System.out.println("Exiting game...");
                    wantsToPlayAgain = false;
                    done = true;
                    for (int i = 0; i < guesses.length; ++i)
                    {
                        guesses[i] = INVALID_NUMBER;
                    }
                    secretNumber = getRandomNumber();
                    System.exit(0);
                    return false;

                } else
                {
                    System.out.println("You did NOT enter an integer in the valid"
                            + " range! Please enter a valid integer: '1' to play" + " again or '0' to quit");
                }
            } else
            {
                in.nextLine();
                System.out.println("You did NOT enter an" + " integer. Please enter a valid integer: '1' to play "
                        + "again or '0' to quit");
            }
        }
        return wantsToPlayAgain;
    }

    /**
     * Resets the game so that the user can have a new secret number to guess and a
     * new set of 7 available guesses
     */
    public void reset()
    {
        int guessesMade1 = guessesMade - 1;
        guessesMade = 0;

        for (int i = 0; i < guesses.length; ++i)
        {
            guesses[i] = INVALID_NUMBER;
        }
        secretNumber = getRandomNumber();

        System.out.println("A new secret number has been picked!");

        System.out.println();

        if (guessesMade == 0)
        {
            welcomeMessage();
        } else if (secretNumber == guesses[guessesMade1])
        {
            welcomeMessage();
        }
        guessesMade1 = 0;
    }

    /**
     * Starts the Guessing Game and determines if the user wishes to play again.
     * While the user still has remaining guesses, the user will be allowed to make
     * another guess.
     * 
     * @return true if the user wishes to play again
     */
    public boolean playGuessingGame()
    {
        boolean goAgain = true;
        while (goAgain)
        {
            reset();

            while (guessesLeft() > 0) // Loop executes while there are guesses remaining
            {
                isGuessNum();
            }
            goAgain = playGame();

        }
        return goAgain;
    }
}