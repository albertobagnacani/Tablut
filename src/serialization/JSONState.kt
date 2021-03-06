package serialization

import model.state.NormalState
import model.state.State
import model.state.player.NormalPlayer
import java.io.File

class JSONState(val newStateString: String, val oldState: NormalState) {
    //state = this.gson.fromJson(read, NormalState::class.java) // TODO1 fare con GSON
    fun deserialize(): State{ // TODO2 migliorare
        var res = oldState.copy()

        val a = newStateString.split("\"")
        val b = a.filter { s -> s == "EMPTY" || s == "WHITE" || s == "BLACK" || s == "KING" || s == "THRONE" }

        val tmp = File("tmpNormalBoardContent.txt")
        tmp.writeText("")
        var char = ""
        for(i in 0..b.size-2) {
            when (b[i]) {
                "EMPTY" -> char = "E"
                "BLACK" -> char = "B"
                "WHITE" -> char = "W"
                "KING" -> char = "K"
                "THRONE" -> char = "E"
            }
            if(i==0 || (i+1)%9!=0){
                tmp.appendText(char+" ")
            }else{
                tmp.appendText(char+"\n")
            }
        }

        //res.board.initializeCoords()
        //res.board.initializeType()
        res.board.emptyContent()
        res.board.initializeContent(tmp)
        //println(newStateString)
        //tmp.forEachLine { println(it) }
        //res.board.printBoard(2)
        tmp.delete()
        res.player = if(b[b.size-1] == "WHITE") NormalPlayer.WHITE else NormalPlayer.BLACK

        return res
    }
}

/*
    {"board":[
        ["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
        ["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
        ["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
        ["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
        ["BLACK","BLACK","WHITE","WHITE","KING","WHITE","WHITE","BLACK","BLACK"],
        ["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
        ["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
        ["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
        ["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
        ],
    "turn":"WHITE"}
 */