from PIL import Image
import numpy as np

############################################################
############################################################
############################################################

name = "silver_black_dalek.png"

to_dots = (82, 82, 82)
to_body = (125, 125, 125) 
to_body2 = (100, 100, 100) 

############################################################
############################################################
############################################################

im = Image.open(name)
im = im.convert('RGBA')

data = np.array(im)
red, green, blue, alpha = data.T

dots = (red == 237) & (green == 181) & (blue == 8)
body = (red == 187) & (green == 121) & (blue == 7)
body2 = (red == 204) & (green == 145) & (blue == 4)
data[..., :-1][dots.T] = to_dots
data[..., :-1][body.T] = to_body
data[..., :-1][body2.T] = to_body2

im2 = Image.fromarray(data)
im2.save(name)