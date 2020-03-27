package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import model.Stock;
import repository.StockRepository;
// https://www.javaguides.net/2020/01/spring-boot-hibernate-oracle-crud-example.html
@RestController
@RequestMapping("/api/Stock")
public class StockController {

	@Autowired StockRepository stockRepo;
	
	@GetMapping("/api/Stock/stocks")
	public List<Stock> getStocks()
	{
		return stockRepo.findAll();
	}
	
	/*@GetMapping("/api/Stock/stocks/{id}")
	public Stock getStockById(long id)
	{
		return stockRepo.findById(id).orElseThrow(); //add exception
	}
	*/
	@GetMapping("/api/Stock/stocks/{id}")
    public ResponseEntity < Stock > getStockById(@PathVariable(value = "id") Long stockId)
    throws ResourceNotFoundException {
        Stock stock = stockRepo.findById(stockId)
            .orElseThrow(() -> new ResourceNotFoundException("Stock not found for this id :: " + stockId));
        return ResponseEntity.ok().body(stock);
    }
	
	/*@GetMapping("/api/Stock/stocks/{CompanyName}")
    public ResponceEntity < Stock > getStockByCompanyName(@PathVariable(value = "CompanyName") Long companyName)
    throws ResourceNotFoundException {
        Stock stock = stockRepo.findByCompanyName(companyName)
            .orElseThrow(() -> new ResourceNotFoundException("Stock not found for this Company :: " + companyName));
        return ResponseEntity.ok().body(stock);
    }
    */

    @PostMapping("/api/Stock/stocks")
    public Stock createStock(@Valid @RequestBody Stock stock) {
        return stockRepo.save(stock);
    }

    @PutMapping("/api/Stock/stocks/{id}")
    public ResponseEntity < Stock > updateStock(@PathVariable(value = "id") Long stockId,
        @Valid @RequestBody Stock stockDetails) throws ResourceNotFoundException {
        Stock stock = stockRepo.findById(stockId)
            .orElseThrow(() -> new ResourceNotFoundException("Stock not found for this id :: " + stockId));

        stock.setCompanyName(stockDetails.getCompanyName());
        stock.setCategory(stockDetails.getCategory());
        stock.setPrice(stockDetails.getPrice());
        stock.setQuantityBought(stockDetails.getQuantityBought());
        final Stock updatedstock = stockRepo.save(stock);
        return ResponseEntity.ok(updatedstock);
    }

    @DeleteMapping("/api/Stock/stocks/{id}")
    public Map < String, Boolean > deleteStock(@PathVariable(value = "id") Long stockId)
    throws ResourceNotFoundException {
        Stock stock = stockRepo.findById(stockId)
            .orElseThrow(() -> new ResourceNotFoundException("Stock not found for this id :: " + stockId));

        stockRepo.delete(stock);
        Map < String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
}
}
