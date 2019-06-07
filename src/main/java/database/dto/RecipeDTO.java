package database.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class RecipeDTO implements Serializable, IRecipeDTO {

	private int recipeId;
	private int productId;
	private List<Double> amount;
	private Date date;
	private List<Integer> pharmaList;
	private List<Integer> ingList;

	@Override
	public int getRecipeId() {
		return recipeId;
	}
	@Override
	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}
	@Override
	public int getProductId() {
		return productId;
	}
	@Override
	public void setProductId(int productId) {
		this.productId = productId;
	}
	@Override
	public List<Double> getAmount(){
		return amount;
	}
	@Override
	public void setAmount(List<Double> amount){
		this.amount = amount;
	}
	@Override
	public Date getDate() {
		return date;
	}
	@Override
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public List<Integer> getPharmaList(){
		return pharmaList;
	}
	@Override
	public void setPharmaList(List<Integer> pharmaList) {
		this.pharmaList = pharmaList;
	}
	@Override
	public List<Integer> getIngList(){
		return ingList;
	}
	@Override
	public void setIngList(List<Integer> ingList) {
		this.ingList = ingList;
	}
	@Override
	public String toString(){
		return "" + recipeId + productId + amount.toString() + date.toString() + pharmaList.toString() + ingList.toString();
	}
}
