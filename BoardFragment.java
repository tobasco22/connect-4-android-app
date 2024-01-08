package com.example.connectfour;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.graphics.drawable.Drawable;


public class BoardFragment extends Fragment {
    private final String GAME_STATE = "gameState";
    private ConnectFourGame mGame;
    private GridLayout mGrid;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_board, container, false);

        //click handler to all grid buttons
        mGrid = parentView.findViewById(R.id.board_grid);

        for (int i = 0; i < mGrid.getChildCount(); i++) {
            Button gridButton = (Button) mGrid.getChildAt(i);
            gridButton.setOnClickListener(this::onButtonClick);
        }

        //Instantiate the member variable of class ConnectFourGame
        mGame = new ConnectFourGame();

        //If savedInstanceState is equal to null, call method startGame
        if (savedInstanceState == null) {
            startGame();
        } else {
            //If savedInstanceState is not null, restore the game state
            String gameState = savedInstanceState.getString(GAME_STATE);
            mGame.setState(gameState);
            setDisc();
        }

        return parentView;
    }

    // Update method onButtonClick
    private void onButtonClick(View view) {
        int buttonIndex = mGrid.indexOfChild(view);
        int row = buttonIndex / ConnectFourGame.ROW;
        int col = buttonIndex % ConnectFourGame.COL;

        //Call method selectDisc in class ConnectFourGame
        mGame.selectDisc(row, col);

        //Call method setDisc
        setDisc();

        //If the game is over, show a toast, start a new game, and update the board
        if (mGame.isGameOver()) {
            //Instantiate a Toast to congratulate the player if they won the game
            Toast.makeText(requireContext(), "Congratulations! You won!", Toast.LENGTH_SHORT).show();

            //Call method newGame in class ConnectFourGame
            mGame.newGame();

            //Call setDisc
            setDisc();
        }
    }

    //method startGame
    private void startGame() {
        mGame.newGame();
        setDisc();
    }

    //method setDisc
    private void setDisc() {
        for (int buttonIndex = 0; buttonIndex < mGrid.getChildCount(); buttonIndex++) {
            Button gridButton = (Button) mGrid.getChildAt(buttonIndex);
            int row = buttonIndex / ConnectFourGame.ROW;
            int col = buttonIndex % ConnectFourGame.COL;

            Drawable discDrawable;
            int discColor = mGame.getDisc(row, col);

            //Drawable for the disc color
            switch (discColor) {
                case ConnectFourGame.BLUE:
                    discDrawable = ContextCompat.getDrawable(requireActivity(), R.drawable.circle_blue);
                    break;
                case ConnectFourGame.RED:
                    discDrawable = ContextCompat.getDrawable(requireActivity(), R.drawable.circle_red);
                    break;
                default:
                    discDrawable = ContextCompat.getDrawable(requireActivity(), R.drawable.circle_white);
                    break;
            }

            //Set background of button
            gridButton.setBackground(DrawableCompat.wrap(discDrawable));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_STATE, mGame.getState());
    }
}
