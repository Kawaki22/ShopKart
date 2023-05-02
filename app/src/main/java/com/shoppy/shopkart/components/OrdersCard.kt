package com.shoppy.shopkart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shoppy.shopkart.R
import com.shoppy.shopkart.models.MCart
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.screens.cart.CartScreenViewModel
import com.shoppy.shopkart.ui.theme.roboto
import java.text.DecimalFormat


@Composable
fun OrdersCard(cardList: List<MCart>,
//               viewModel: CartScreenViewModel,
             navController: NavController,
//             priceLists: (Int) -> Unit
){

    val priceList: MutableList<Int> = mutableListOf()

    Column(modifier = Modifier
        .padding(bottom = 10.dp)
        .verticalScroll(rememberScrollState())) {


        for (card in cardList){
            OrdersCardItem(mOrder = card, navController = navController, price = {price -> priceList.add(price)})
//            Log.d("PRICEES", "CartCard: ${priceList}")
        }

    }
}

@Composable
fun OrdersCardItem(mOrder: MCart, navController: NavController,price: (Int) -> Unit
) {

    val countState = remember { mutableStateOf(mOrder.item_count) }

    price(mOrder.product_price!! * countState.value!!)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 8.dp)
            .clickable { navController.navigate(BottomNavScreens.MyOrderDetails.route) },
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .width(100.dp)
                    .fillMaxHeight()
                    .padding(start = 10.dp, top = 10.dp, bottom = 8.dp)
            ) {

                AsyncImage(
                    model = mOrder.product_url,
                    contentDescription = mOrder.product_title,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    contentScale = ContentScale.Inside
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, end = 25.dp, top = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = mOrder.product_title!!,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.width(200.dp)
                )

                Text(
                    text = mOrder.product_description!!,
                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal, fontFamily = roboto),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.width(180.dp)
                )

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(top = 8.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = "₹${
                            DecimalFormat("#,##,###").format(
                                mOrder.product_price.toString().toDouble()
                            )
                        }",
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(modifier = Modifier.width(60.dp))

                    Text(
                        text = "Delivered",
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Icon(modifier = Modifier.padding(start = 5.dp, top = 8.dp), imageVector = Icons.Rounded.CheckCircle, contentDescription = "Delivery Status")

                }


            }
        }
    }
}