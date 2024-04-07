package com.example.compose.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.testmaterial3.R

@Preview(showSystemUi = true, device = Devices.PIXEL_3)
@Composable
fun ConstraintLayoutContentPreview() {
    MaterialTheme {
        ConstraintLayoutContent()
    }
}

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        val (title, icon1, topDivider, param1, param2, div1, param3, param4, div2) = createRefs()

        val startGuide = createGuidelineFromStart(0.dp)
        val topGuide = createGuidelineFromTop(56.dp)
        val endGuide = createGuidelineFromEnd(0.dp)

        Text(
            text = "Dashboard",
            fontWeight = FontWeight.Medium,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start, 16.dp)
                top.linkTo(icon1.top)
                end.linkTo(icon1.start, margin = 16.dp)
                bottom.linkTo(icon1.bottom)
                width = Dimension.fillToConstraints
            })

        Image(
            painter = painterResource(R.drawable.ic_person),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            modifier = Modifier
                .size(48.dp)
                .constrainAs(icon1) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, margin = 8.dp)
                    bottom.linkTo(topGuide)
                }
        )

        HorizontalDivider(Modifier.constrainAs(topDivider) {
            start.linkTo(parent.start)
            top.linkTo(topGuide)
            end.linkTo(parent.end)
        })


        DashboardStat(
            modifier = Modifier.constrainAs(param1) {
                start.linkTo(startGuide)
                top.linkTo(topGuide, margin = 8.dp)
                end.linkTo(param2.start)
                width = Dimension.fillToConstraints
            },
            head = "Health"
        )

        DashboardStat(
            modifier = Modifier.constrainAs(param2) {
                start.linkTo(param1.end)
                top.linkTo(param1.top)
                end.linkTo(endGuide)
                width = Dimension.fillToConstraints
            },
            head = "Assist"
        )

        HorizontalDivider(modifier = Modifier.constrainAs(div1) {
            start.linkTo(parent.start, margin = 8.dp)
            top.linkTo(param1.bottom, margin = 8.dp)
            end.linkTo(parent.end, margin = 8.dp)
            width = Dimension.fillToConstraints
        })

        DashboardStat(
            modifier = Modifier.constrainAs(param3) {
                start.linkTo(startGuide)
                top.linkTo(div1.bottom, margin = 8.dp)
                end.linkTo(param4.start)
                width = Dimension.fillToConstraints
            },
            head = "Attend"
        )

        DashboardStat(
            modifier = Modifier.constrainAs(param4) {
                start.linkTo(param3.end)
                top.linkTo(param3.top)
                end.linkTo(endGuide)
                width = Dimension.fillToConstraints
            },
            head = "Help"
        )

        HorizontalDivider(modifier = Modifier.constrainAs(div2) {
            start.linkTo(parent.start, margin = 8.dp)
            top.linkTo(param3.bottom, margin = 8.dp)
            end.linkTo(parent.end, margin = 8.dp)
            width = Dimension.fillToConstraints
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardStat(
    modifier: Modifier = Modifier,
    head: String = "Title"
) {
    ConstraintLayout(modifier) {
        val (title, ctr, pg, ph, div, stat, profile, btn) = createRefs()

        val gl1 = createGuidelineFromStart(0.2f)
        val gl2 = createGuidelineFromStart(0.5f)
        val gl3 = createGuidelineFromStart(0.8f)

        Text(
            text = head,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        )

        StatItemView(
            param = "Stat",
            value = "89",
            icon = R.drawable.ic_article,
            modifier = Modifier.constrainAs(stat) {
                start.linkTo(gl1)
                top.linkTo(title.bottom, margin = 8.dp)
                end.linkTo(gl2)
            }
        )

        StatItemView(
            param = "Prof",
            value = "67",
            icon = R.drawable.ic_photo,
            modifier = Modifier.constrainAs(profile) {
                start.linkTo(gl2)
                top.linkTo(stat.top)
                end.linkTo(gl3)
            }
        )

        HorizontalDivider(modifier = Modifier.constrainAs(div) {
            start.linkTo(ctr.start)
            top.linkTo(stat.bottom, margin = 8.dp)
            end.linkTo(ph.end)
            width = Dimension.fillToConstraints
        })

        StatItemView(
            param = "Ctr",
            value = "34",
            icon = R.drawable.ic_android,
            modifier = Modifier.constrainAs(ctr) {
                start.linkTo(gl1)
                top.linkTo(div.bottom, margin = 8.dp)
                end.linkTo(gl1)
            }
        )

        StatItemView(
            param = "Pg",
            value = "89",
            icon = R.drawable.ic_android,
            modifier = Modifier.constrainAs(pg) {
                start.linkTo(gl2)
                top.linkTo(ctr.top)
                end.linkTo(gl2)
            }
        )

        StatItemView(
            param = "Ph",
            value = "45",
            icon = R.drawable.ic_android,
            modifier = Modifier.constrainAs(ph) {
                start.linkTo(gl3)
                top.linkTo(ctr.top)
                end.linkTo(gl3)
            }
        )

        TextButton(
            onClick = { },
            modifier = Modifier.constrainAs(btn) {
                top.linkTo(ctr.bottom, margin = 16.dp)
                end.linkTo(parent.end, 16.dp)
            }
        ) {
            Text(text = "View")
        }
    }
}

@Composable
fun StatItemView(param: String, value: String, icon: Int, modifier: Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = buildAnnotatedString {
                append(param)
                append(": ")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                ) {
                    append(value)
                }
            }
        )
    }
}
