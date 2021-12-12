package project.schemiq.Service;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import project.schemiq.SchemiqApplication;
import project.schemiq.model.BoardModel;

import project.schemiq.repository.BoardRepository;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private BoardRepository boardRepository;

    public BoardModel createBoard(BoardModel boardModel) {
        BoardModel newBoardRepository = boardModel;
        return boardRepository.save(newBoardRepository);
    }


    public BoardModel findBoardByID(Long ID){
        Optional<BoardModel> board = boardRepository.findById(ID);
        if(board.isPresent()){
            return board.get();
        }
        throw new ObjectNotFoundException(UserService.class, SchemiqApplication.elementNotFound);
    }

    public List<BoardModel> getEverything(){
        return boardRepository.findAll();
    }

    public void deleteAll(){
        boardRepository.deleteAll();
    }

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;

    }

    public BoardModel findOne(BoardModel boardModel) {
        Optional<BoardModel> board = boardRepository.findById(boardModel.getId());
        if(board.isPresent()){

            return board.get();
        }
        return null;
    }

}
