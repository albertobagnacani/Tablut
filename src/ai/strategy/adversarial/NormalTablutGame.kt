package ai.strategy.adversarial

import ai.action.*
import model.state.NormalState
import model.state.State

import model.state.StateFactory
import model.state.player.NormalPlayer
import model.state.player.Player
import model.state.rules.GameRules
import model.state.rules.NormalGameRules

class NormalTablutGame(val state: State, val initialState: NormalState, val gameRules: NormalGameRules, val actionResolver: ActionResolver) : TablutGame {
    /**
     * The initial state, which specifies how the game is set up at the start
     */
    override fun getInitialState(): State = initialState

    /**
     * The transition model, which defines the result of a move.
     */
    override fun getResult(state: State, action: Action): State {
       return (state as NormalState).applyAction(action as NormalTablutAction)
    }

    /**
     * Defines which player has the move in a state.
     */
    override fun getPlayer(state: State): Player {
        return state.player
    }

    // TODO1 mettere Array<Player>
    override fun getPlayers(): Array<NormalPlayer> {
        return NormalPlayer.values()
    }

    /**
     * Returns the set of legal moves in a state.
     */
    override fun getActions(state: State): List<Action> {
        return actionResolver.actions(state)
    }

    /**
     * A utility function (also called an objective function or
     * payoff function), defines the final numeric value for a game that ends in
     * terminal state s for a player p. In chess, the outcome is a win, loss, or
     * draw, with values +1, 0, or 1/2 . Some games have a wider variety of possible
     * outcomes; the payoffs in backgammon range from 0 to +192. A zero-sum game is
     * (confusingly) defined as one where the total payoff to all players is the
     * same for every instance of the game. Chess is zero-sum because every game has
     * payoff of either 0 + 1, 1 + 0 or 1/2 + 1/2 . "Constant-sum" would have been a
     * better term, but zero-sum is traditional and makes sense if you imagine each
     * player is charged an entry fee of 1/2.
     */
    // TODO1 sistemare NormalGameRules(state).isTerminal()
    // TODO1 hardcoded 1 and 0
    // TODO2 patta come hash dello stato
    override fun getUtility(state: State, player: Player): Double {
        return if(NormalGameRules(state as NormalState).isTerminal() && initialState.player == state.player) 1.0 else -1.0 // TODO patta
    }

    /**
     * A terminal test (as goal test as in the informed), which is true when the game is over
     * and false TERMINAL STATES otherwise.
     * States where the game has ended are called terminal states
     */
    // TODO1 why these cast? Everywhere
    override fun isTerminal(state: State): Boolean {
        return (gameRules as NormalGameRules).isTerminal()
    }
}