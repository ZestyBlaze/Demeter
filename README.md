# Better-Farms

## Inspiration
A lot of the inspiration for this mod comes from multiple parts, a lot of credit goes to the old Harvest Festival mod, which this is very much based off of, while drawing a lot of inspiration from games such as Stardew Valley and Harvest Moon!
I wanted to create a mod that felt like Harvest Festival again while putting my own twist on it, and from those thoughts came.. whatever this is

## Configuration
Most of this mod can be configured with either changing values around in `config/betterfarms-common.json` or you can provide integration with this mod and other animal mods by adding onto the custom datapack in this mod!
For example, to add support for a cow, the file is constructed as such:
```json
{
  "daysPregnant": 9,
  "milking": {}
}
```

This means the manager will now recognise the `minecraft:cow` as a valid target for the mod and because a value for `daysPregnant` has been passed, it will take 9 days for a birth to occur, as well as accepting the animal as a valid milking target with the default input and output items

To add support for a new mob that the mod does not provide default support for, you can create a json file in the following location: `data/{mod_name}/betterfarms/stats/{mob_id}.json`, where `{mod_name}` is the Mod ID of the mod and the `{mob_id}` is the registered ID for it (E.g. `cow` for a cow)

The full list of valid values for the JSON are as follows, with their defaults:

- "daysPregnant" (Default = 0): How many days the specified animal will take to give birth. A value of 0 will give birth immediately
- "minChildrenPerBirth" (Default = 1): The min size a birth litter can be. Note: Should **never** be bigger than `maxChildrenPerBirth`
- "maxChildrenPerBirth" (Default = 1): The max size a birth litter can be. Note: Value should **never** be smaller than `minChildrenPerBirth`
- "milking" (Default = not applied, input = "minecraft:bucket", output = "minecraft:milk_bucket"): If the mod should add support for milking to the animal with custom support for input and output (NOT VALID: NEEDS CHANGING. New Section?)
- "breedingItems" (Default = null): A new option which allows you to override breeding items for mobs. Not being present causes it to use default values

### Milking
{Milking}

### Breeding Items
It is possible to change what mobs accept what as their valid breeding items by specifying an `Ingredient` within `breedingItems`
For example, if I wanted to add carrots, beetroots and any flower from the flower tag as the cow's breeding items, you can write the following in `cow.json`
```json
{
  "breedingItems": [
    {
      "item": "minecraft:carrot"
    },
    {
      "item": "minecraft:beetroot"
    },
    {
      "tag": "minecraft:flowers"
    }
  ]
}
```