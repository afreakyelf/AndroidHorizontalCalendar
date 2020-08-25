package com.example.horizontalcalendar


import android.app.DatePickerDialog
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.horizontalcalendar.AdapterForDates.Companion.attributes
import kotlinx.android.synthetic.main.calenderview.view.*
import kotlinx.android.synthetic.main.date_item.view.*
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HorizontalCalender @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var mFirstCompleteVisibleItemPosition = -1
    private var mLastCompleteVisibleItemPosition = -1
    private var mEndDate: Date? = null
    private var mMonths = DateFormatSymbols().months
    private var mStartD: Date? = null
    private lateinit var mBaseDateList: ArrayList<Date>
    private val mCal = Calendar.getInstance()
    private var dates_rv: RecyclerView? = null
    private var month: TextView? = null
    private var mFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)
    private var mTextColor: Int? = null
    private var mShowTodayIcon: Boolean? = null

    init {
        View.inflate(context, R.layout.calenderview, this)
        attributes = context.obtainStyledAttributes(attrs, R.styleable.HorizontalCalender)
        dates_rv = findViewById(R.id.dates_rv)
        month = findViewById(R.id.month)
        setShowTodayIcon(
            attributes?.getBoolean(
                R.styleable.HorizontalCalender_showTodayIcon,
                false
            )
        )
        setTextColorA(attributes!!.getColor(R.styleable.HorizontalCalender_android_textColor, 0))
        month?.setTextColor(getTextColorA()!!)
    }

    fun initialize(dateItemClickListener: DateItemClickListener){
        horizontalDates(dateItemClickListener)
    }

    private fun horizontalDates(dateItemClickListener: DateItemClickListener) {
        mFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        mStartD = Date()
        val calendar = Calendar.getInstance()
        calendar.time = mStartD
        val currentMonth = mMonths[calendar.get(Calendar.MONTH)]
        val currentYear = calendar.get(Calendar.YEAR).toString()
        month?.text = "$currentMonth ,$currentYear"
        calendar.add(Calendar.MONTH, -1)
        mEndDate = calendar.time
        mBaseDateList = getDates(mFormatter.format(mEndDate), mFormatter.format(mStartD))
        setAdapter(mBaseDateList,dateItemClickListener)
        val layoutManager3 = dates_rv?.layoutManager as LinearLayoutManager
        layoutManager3.scrollToPosition(mBaseDateList.size - 1)

    }


    private fun getDates(dateString1: String, dateString2: String): ArrayList<Date> {

        val mDates = ArrayList<Date>()
        var startDate: Date? = null
        try {
            startDate = mFormatter.parse(dateString1)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        var endDate: Date? = null
        try {
            endDate = mFormatter.parse(dateString2)
        } catch (e: ParseException) {

            e.printStackTrace()
        }

        val interval = (24 * 1000 * 60 * 60).toLong()
        val endTime = endDate!!.time
        var curTime = startDate!!.time
        while (curTime <= endTime) {
            mDates.add(Date(curTime))
            curTime += interval
        }

        return mDates

    }

    private fun setAdapter(
        dates: ArrayList<Date>,
        dateItemClickListener: DateItemClickListener
    ) {

        val mFinalDates = ArrayList<Model>()
        val clickedDate = ArrayList<String>()


        for (i in 0 until dates.size) {
            val lDate = dates[i]
            val c = Calendar.getInstance()
            c.time = lDate

            val dayOfWeek = c.get(Calendar.DATE)
            val monthh = c.get(Calendar.MONTH)
            val year = c.get(Calendar.YEAR)

            val dayLongName =
                c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
            mFinalDates.add(Model(dayOfWeek.toString(), dayLongName))
            clickedDate.add(String.format("%02d-%02d-%04d", dayOfWeek, monthh + 1, year))

        }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        layoutManager.stackFromEnd = true
        dates_rv?.layoutManager = layoutManager
        layoutManager.scrollToPosition(30)

        dates_rv?.adapter = AdapterForDates(mFinalDates, context, clickedDate, dates,dateItemClickListener)

        today?.setOnClickListener {
            layoutManager.scrollToPosition(mFinalDates.size - 1)
            today.visibility = View.GONE
        }

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                mCal.set(Calendar.YEAR, year)
                mCal.set(Calendar.MONTH, monthOfYear)
                mCal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                Log.d("date", mCal.time.toString())
                val calendarSelect = Calendar.getInstance()
                calendarSelect.time = mCal.time
                calendarSelect.add(Calendar.MONTH, -1)
                val endDate = calendarSelect.time
                month?.text =
                    mMonths[calendarSelect.get(Calendar.MONTH)] + ", " + calendarSelect.get(Calendar.YEAR)
                mBaseDateList = getDates(mFormatter.format(endDate), mFormatter.format(mStartD))
                setAdapter(mBaseDateList, dateItemClickListener)
                mEndDate = endDate
                if (mBaseDateList.size > 30) {
                    dates_rv?.smoothScrollToPosition(32)
                } else {
                    dates_rv?.smoothScrollToPosition(30)
                }
            }



        month?.setOnClickListener {
            DatePickerDialog(
                context,
                dateSetListener,
                mCal.get(Calendar.YEAR),
                mCal.get(Calendar.MONTH),
                mCal.get(Calendar.DAY_OF_MONTH)
            ).show()
            mCal.add(Calendar.MONTH, -1)
        }


        dates_rv?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManagerForPos = recyclerView.layoutManager
                val totalItemCount = layoutManagerForPos?.itemCount

                if (layoutManagerForPos is GridLayoutManager) {
                    val gridLayoutManager = layoutManagerForPos as GridLayoutManager?
                    mFirstCompleteVisibleItemPosition =
                        gridLayoutManager!!.findFirstCompletelyVisibleItemPosition()
                    mLastCompleteVisibleItemPosition =
                        gridLayoutManager.findLastCompletelyVisibleItemPosition()
                } else if (layoutManagerForPos is LinearLayoutManager) {
                    val linearLayoutManager = layoutManagerForPos as LinearLayoutManager?
                    mFirstCompleteVisibleItemPosition =
                        linearLayoutManager!!.findFirstCompletelyVisibleItemPosition()
                    mLastCompleteVisibleItemPosition =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition()
                }
                if (mFirstCompleteVisibleItemPosition == 0) {
                    if (dy < 0 || dx < 0) {
                        if (dx < 0) {
                            Log.d("status", "Scrolled LEFT")
                            if (getShowTodayIcon()!!) today.visibility = View.VISIBLE
                            val calendar = Calendar.getInstance()
                            calendar.time = mEndDate
                            calendar.add(Calendar.MONTH, -1)
                            val tempEndDate = calendar.time
                            month?.text =
                                mMonths[calendar.get(Calendar.MONTH)] + ", " + calendar.get(Calendar.YEAR)
                            mBaseDateList =
                                getDates(mFormatter.format(tempEndDate), mFormatter.format(mStartD))
                            setAdapter(mBaseDateList, dateItemClickListener)
                            mEndDate = tempEndDate
                        }
                    }
                } else if (totalItemCount != null) {
                    if (mLastCompleteVisibleItemPosition == totalItemCount - 1) {
                        val calendar = Calendar.getInstance()
                        val date = mBaseDateList[mLastCompleteVisibleItemPosition]
                        calendar.time = date
                        month?.text =
                            mMonths[calendar.get(Calendar.MONTH)] + ", " + calendar.get(Calendar.YEAR)

                        if (dy > 0 || dx > 0) {
                            if (dy > 0) {
                                Log.d("status", "Scrolled TOP")
                            }
                            if (dx > 0) {
                                Log.d("status", "Scrolled RIGHT")
                                today.visibility = View.GONE
                            }
                        }
                    } else {
                        if (dx < 0) {
                            Log.d("status", "Scrolled LEFT")
                            if (getShowTodayIcon()!!) today.visibility = View.VISIBLE
                            val calendar = Calendar.getInstance()
                            val date = mBaseDateList[mFirstCompleteVisibleItemPosition + 1]
                            calendar.time = date
                            month?.text =
                                mMonths[calendar.get(Calendar.MONTH)] + ", " + calendar.get(Calendar.YEAR)
                        } else {
                            val calendar = Calendar.getInstance()
                            val date = mBaseDateList[mLastCompleteVisibleItemPosition]
                            calendar.time = date
                            month?.text =
                                mMonths[calendar.get(Calendar.MONTH)] + ", " + calendar.get(Calendar.YEAR)
                        }
                    }
                }
            }
        })
    }

    private fun setTextColorA(int: Int) {
        this.mTextColor = int
    }

    private fun getTextColorA(): Int? {
        return mTextColor
    }


    private fun setShowTodayIcon(image: Boolean?) {
        this.mShowTodayIcon = image
    }

    private fun getShowTodayIcon(): Boolean? {
        return mShowTodayIcon
    }

}

private class AdapterForDates(
    private val mModelItems: ArrayList<Model>,
    private val mContext: Context,
    private val mDatesList: ArrayList<String>,
    private val fullFormatDate: ArrayList<Date>,
    private val dateItemClickListener: DateItemClickListener
) : RecyclerView.Adapter<AdapterForDates.ViewHolder>() {

    private var mPreviousIndex: Int? = -1
    private var mStrokeColor: Int? = null
    private var mUnSelectedColor: Int? = null
    private var mSelectedColor: Int? = null
    private var mStrokeWidth: Float? = null


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.date_item, p0, false))
    }

    override fun getItemCount(): Int {
        return mModelItems.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val mMonths = DateFormatSymbols().months
        p0.date?.text = mModelItems[p1].date
        p0.date.setTextColor(
            attributes!!.getColor(
                R.styleable.HorizontalCalender_android_textColor,
                0
            )
        )

        if (p1 == mModelItems.size - 1) {
            p0.day?.text = mContext.getString(R.string.today)
        } else {
            p0.day?.text = mModelItems[p1].day[0].toString()
        }


        if (attributes!!.getBoolean(R.styleable.HorizontalCalender_dayView, true)) {
            p0.day.visibility = View.VISIBLE
        } else {
            p0.day.visibility = View.GONE
        }

        p0.layout.setOnClickListener {
            val date = mDatesList[p1]
            dateItemClickListener.onDateClick(date)

            val calendar = Calendar.getInstance()
            calendar.time = fullFormatDate[p1]
            mMonth = mMonths[calendar.get(Calendar.MONTH)] + ", " + calendar.get(Calendar.YEAR)
            mPreviousIndex = p1
            notifyDataSetChanged()
        }


        setSelectedColorA(attributes?.getColor(R.styleable.HorizontalCalender_selectedColor, 0)!!)
        setUnSelectedColorA(
            attributes?.getColor(
                R.styleable.HorizontalCalender_unSelectedColor,
                0
            )!!
        )
        setStrokeWidthA(attributes?.getDimension(R.styleable.HorizontalCalender_strokeWidth, 0F)!!)
        setStrokeColorA(attributes?.getColor(R.styleable.HorizontalCalender_strokeColor, 0)!!)

        mStrokeColor = getStrokeColorA()
        mUnSelectedColor = getUnSelectedColorA()
        mSelectedColor = getSelectedColorA()
        mStrokeWidth = getStrokeWidthA()


        mStrokeWidth = if (mStrokeWidth == 0F) {
            2f
        } else {
            getStrokeWidthA()
        }

        mStrokeColor = if (mStrokeColor == 0) {
            Color.BLUE
        } else {
            getStrokeColorA()
        }

        mUnSelectedColor = if (mUnSelectedColor == 0) {
            Color.CYAN
        } else {
            getUnSelectedColorA()
        }


        mSelectedColor = if (mSelectedColor == 0) {
            Color.WHITE
        } else {
            getSelectedColorA()
        }


        if (mPreviousIndex == p1) {

            val shape = GradientDrawable()
            shape.shape = GradientDrawable.OVAL
            shape.setStroke(mStrokeWidth!!.toInt(), mStrokeColor!!)


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                shape.setColor(mSelectedColor!!)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    p0.imageView.background = shape
                }

            } else {

                val oval = ShapeDrawable(OvalShape())
                oval.paint.color = mSelectedColor!!
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    p0.imageView.background = oval
                }
            }


        } else {

            val shape = GradientDrawable()
            shape.shape = GradientDrawable.OVAL

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                shape.setColor(mUnSelectedColor!!)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    p0.imageView.background = shape

                }
            } else {

                val oval = ShapeDrawable(OvalShape())
                oval.paint.color = mUnSelectedColor!!
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    p0.imageView.background = oval
                }
            }


        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val date = view.date
        internal val layout = view.layout
        internal val day = view.day
        internal val imageView = view.circleImageView
    }

    companion object {
        var mMonth: String? = null
        var attributes: TypedArray? = null
    }

    private fun setSelectedColorA(color: Int) {
        this.mSelectedColor = color
    }

    private fun getSelectedColorA(): Int? {
        return mSelectedColor
    }

    private fun setUnSelectedColorA(color: Int) {
        this.mUnSelectedColor = color
    }

    private fun getUnSelectedColorA(): Int? {
        return mUnSelectedColor
    }

    private fun setStrokeColorA(color: Int) {
        this.mStrokeColor = color
    }

    private fun getStrokeColorA(): Int? {
        return mStrokeColor
    }

    private fun setStrokeWidthA(width: Float) {
        this.mStrokeWidth = width
    }

    private fun getStrokeWidthA(): Float? {
        return mStrokeWidth
    }

}

data class Model(val date: String, val day: String)