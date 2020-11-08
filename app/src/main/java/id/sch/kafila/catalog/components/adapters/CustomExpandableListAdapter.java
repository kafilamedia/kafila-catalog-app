package id.sch.kafila.catalog.components.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.contents.Dimension;
import id.sch.kafila.catalog.models.ListChildInfo;
import id.sch.kafila.catalog.models.ListGroupInfo;

public class CustomExpandableListAdapter<T> extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<ListGroupInfo> requirementGroups;
    private ExpandableListView parent;
    int lastExpandedGroup = -1;

    public CustomExpandableListAdapter(Context context, ArrayList<ListGroupInfo> requirementGroups, ExpandableListView listView) {
        this.context = context;
        this.requirementGroups = requirementGroups;
        this.parent = listView;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        if(lastExpandedGroup!=groupPosition) {
            parent.collapseGroup(lastExpandedGroup);
        }
        lastExpandedGroup = groupPosition;
//        parent.getLayoutParams().height = Dimension.getScreenHeight(parent.getContext());
//        parent.requestLayout();

        super.onGroupExpanded(groupPosition);

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
//        parent.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        parent.requestLayout();
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ListChildInfo> productList = requirementGroups.get(groupPosition).getChildInfos();
        return productList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        ListChildInfo detailInfo = (ListChildInfo) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.expandable_list_child_item, null);
        }
        TextView numbering = (TextView) view.findViewById(R.id.expandable_list_child_item_number);
        TextView childItem = (TextView) view.findViewById(R.id.expandable_list_child_item);

        numbering.setText(String.valueOf(detailInfo.getNumber()));
        childItem.setText(detailInfo.getName().trim());
        childItem.getLayoutParams().width = Dimension.getScreenWidth(context)*4/5;
        childItem.requestLayout();
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<ListChildInfo> productList = requirementGroups.get(groupPosition).getChildInfos();
        return productList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return requirementGroups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return requirementGroups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        ListGroupInfo headerInfo = (ListGroupInfo) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.expandable_list_group_item, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.expandable_list_headings);
        heading.setText(headerInfo.getName().trim());

        return view;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
