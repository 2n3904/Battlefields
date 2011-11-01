package me.jkow.battlefields.players;

import java.util.ArrayList;
import java.util.List;

import me.jkow.battlefields.util.Aliases;
import me.jkow.battlefields.util.Armor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class TeamMemberClass
{	
	private TeamMember m;
	private Player p;
	
	private String name;
	
	private int damage_reduction;
	private int damage_bonus;
	
	private List<Integer> weapons = new ArrayList<Integer>();
	private Integer[] armor = new Integer[4];
	
	public TeamMemberClass(String name, int bonus, int reduction, List<Integer> weapons, Integer[] armor)
	{
		this.name = name;
		this.damage_bonus = bonus;
		this.damage_reduction = reduction;
		this.weapons = weapons;
		this.armor = armor;
	}	
	
	public String getName()
	{
		return name;
	}
	
	public void setPlayerInventory(Player p)
	{
		PlayerInventory pi = p.getInventory();
		ItemStack[] contents = new ItemStack[pi.getContents().length];
		ItemStack[] armorcontents = new ItemStack[pi.getArmorContents().length];

		// TODO put armor and weps into player inv
	}
	
	public ItemStack getArmorSlot(int slot)
	{
		Armor item = Aliases.getArmor(armor[slot]);
		if(item == null)
			return null;
		if(item.getSlot() != slot)
			return null;
		return new ItemStack(item.getId());
	}

	public List<Integer> getWeaponList()
	{
		return weapons;
	}
	
	public int getDamageBonus()
	{
		return damage_bonus;
	}
	
	public int getDamageReduction()
	{
		return damage_reduction;
	}
	
	
	
}
