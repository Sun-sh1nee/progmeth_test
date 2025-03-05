package card;

public class BaseCard implements Comparable<BaseCard> {
	protected String name;
	protected String CardURL;
	protected CardTier tier;

	public BaseCard(String name, String image, CardTier tier) {
		this.setName(name);
		this.setCardURL(image);
		this.setTier(tier);
		String path = ClassLoader.getSystemResource(image).toString();
		this.setCardURL(path);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardURL() {
		return CardURL;
	}

	public void setCardURL(String cardURL) {
		CardURL = cardURL;
	}

	public CardTier getTier() {
		return tier;
	}

	public void setTier(CardTier tier) {
		this.tier = tier;
	}

	public String getTierStyle() {
		switch (this.tier) {
		case COMMON:
			return "gray";
		case RARE:
			return "blue";
		case EPIC:
			return "purple";
		case LEGENDARY:
			return "orange";
		default:
			return "black;";
		}
	}

	@Override
	public int compareTo(BaseCard other) {
		return Integer.compare(other.tier.ordinal(), this.tier.ordinal());
	}

}
